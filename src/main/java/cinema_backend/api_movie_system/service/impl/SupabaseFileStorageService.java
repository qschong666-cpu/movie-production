package cinema_backend.api_movie_system.service.impl;

import cinema_backend.api_movie_system.config.SupabaseProperties;
import cinema_backend.api_movie_system.models.FileEntity;
import cinema_backend.api_movie_system.repository.FileRepository;
import cinema_backend.api_movie_system.service.FileStorageService;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Service
public class SupabaseFileStorageService implements FileStorageService {

    private final FileRepository fileRepository;
    private final SupabaseProperties supabaseProperties;
    private final RestTemplate restTemplate = new RestTemplate();

    public SupabaseFileStorageService(FileRepository fileRepository, SupabaseProperties supabaseProperties) {
        this.fileRepository = fileRepository;
        this.supabaseProperties = supabaseProperties;
    }

    @Override
    public FileEntity uploadFile(MultipartFile file, String ownerType, Integer ownerId) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File must not be empty");
        }

        String storagePath = UUID.randomUUID() + "-" + sanitizeFilename(file.getOriginalFilename());
        uploadToSupabase(storagePath, file);

        FileEntity fileEntity = new FileEntity();
        fileEntity.setFileUrl(buildStorageUrl(storagePath));
        fileEntity.setStoragePath(storagePath);
        fileEntity.setMimeType(file.getContentType());
        fileEntity.setSizeBytes(file.getSize());
        fileEntity.setOwnerType(ownerType);
        fileEntity.setOwnerId(ownerId);
        fileEntity.setCreatedDate(LocalDateTime.now());

        return fileRepository.save(fileEntity);
    }

    @Override
    public String getSignedUrl(Integer fileId, Duration ttl) {
        FileEntity fileEntity = fileRepository.findById(fileId)
            .orElseThrow(() -> new IllegalArgumentException("File not found with id: " + fileId));

        return getSignedUrlFromSupabase(fileEntity.getStoragePath(), ttl);
    }

    @Override
    public void deleteFile(Integer fileId) {
        FileEntity fileEntity = fileRepository.findById(fileId)
            .orElseThrow(() -> new IllegalArgumentException("File not found with id: " + fileId));

        fileEntity.setIsDeleted(true);
        fileRepository.save(fileEntity);
    }

    private void uploadToSupabase(String storagePath, MultipartFile file) {
        String url = buildStorageUrl(storagePath);
        HttpHeaders headers = buildUploadHeaders(file.getContentType());
        HttpEntity<byte[]> entity = new HttpEntity<>(getFileBytes(file), headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new IllegalStateException("Supabase upload failed with status: " + response.getStatusCode());
            }
        } catch (HttpClientErrorException ex) {
            String responseBody = ex.getResponseBodyAsString();
            if (responseBody != null && responseBody.contains("NoSuchBucket")) {
                throw new IllegalStateException(
                    "Supabase bucket '" + supabaseProperties.getBucket() + "' was not found. "
                        + "Create this bucket in Supabase Storage or set app.supabase.bucket to an existing bucket.",
                    ex
                );
            }

            throw new IllegalStateException(
                "Supabase upload failed: " + ex.getStatusCode() + " " + responseBody,
                ex
            );
        }
    }

    private String getSignedUrlFromSupabase(String storagePath, Duration ttl) {
        String encodedPath = encodeStoragePath(storagePath);
        String url = supabaseProperties.getUrl()
            + "/storage/v1/object/sign/"
            + supabaseProperties.getBucket()
            + "/"
            + encodedPath;

        HttpHeaders headers = buildHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, Object> payload = new HashMap<>();
        payload.put("expiresIn", ttl.getSeconds());
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(payload, headers);
        ResponseEntity<Map> response = restTemplate.exchange(URI.create(url), HttpMethod.POST, entity, Map.class);
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new IllegalStateException("Supabase signed URL generation failed with status: " + response.getStatusCode());
        }

        Object signedUrl = null;
        if (response.getBody() != null) {
            signedUrl = response.getBody().get("signedURL");
            if (signedUrl == null) {
                signedUrl = response.getBody().get("signedUrl");
            }
        }
        if (signedUrl == null) {
            throw new IllegalStateException("Supabase did not return a signed URL");
        }

        String signed = signedUrl.toString();
        if (signed.startsWith("http://") || signed.startsWith("https://")) {
            return signed;
        }

        // Supabase returns a relative signed path like "/object/sign/...".
        // It must be resolved under "/storage/v1" to become a valid downloadable URL.
        if (signed.startsWith("/object/")) {
            return supabaseProperties.getUrl() + "/storage/v1" + signed;
        }

        return supabaseProperties.getUrl() + signed;
    }

    private HttpHeaders buildHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("apikey", supabaseProperties.getKey());
        headers.set("Authorization", "Bearer " + supabaseProperties.getKey());
        return headers;
    }

    private HttpHeaders buildUploadHeaders(String contentType) {
        HttpHeaders headers = buildHeaders();
        headers.setContentType(parseMediaType(contentType));
        headers.set("x-upsert", "true");
        return headers;
    }

    private MediaType parseMediaType(String contentType) {
        if (contentType == null || contentType.isBlank()) {
            return MediaType.APPLICATION_OCTET_STREAM;
        }
        try {
            return MediaType.parseMediaType(contentType);
        } catch (Exception ignored) {
            return MediaType.APPLICATION_OCTET_STREAM;
        }
    }

    private String buildStorageUrl(String storagePath) {
        String bucket = supabaseProperties.getBucket();
        return supabaseProperties.getUrl() + "/storage/v1/object/" + bucket + "/" + encodeStoragePath(storagePath);
    }

    private String encodeStoragePath(String storagePath) {
        return URLEncoder.encode(storagePath, StandardCharsets.UTF_8)
            .replace("+", "%20")
            .replace("%2F", "/");
    }

    private byte[] getFileBytes(MultipartFile file) {
        try {
            return file.getBytes();
        } catch (Exception e) {
            throw new IllegalStateException("Could not read uploaded file", e);
        }
    }

    private String sanitizeFilename(String originalFilename) {
        if (originalFilename == null || originalFilename.isBlank()) {
            return UUID.randomUUID().toString();
        }
        return originalFilename.replaceAll("[^a-zA-Z0-9._-]", "-");
    }
}
