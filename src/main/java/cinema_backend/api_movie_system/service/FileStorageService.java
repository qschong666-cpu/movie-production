package cinema_backend.api_movie_system.service;

import cinema_backend.api_movie_system.models.FileEntity;
import java.time.Duration;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    FileEntity uploadFile(MultipartFile file, String ownerType, Integer ownerId);

    String getSignedUrl(Integer fileId, Duration ttl);

    void deleteFile(Integer fileId);
}
