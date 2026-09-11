package cinema_backend.api_movie_system.controller;

import cinema_backend.api_movie_system.models.FileEntity;
import cinema_backend.api_movie_system.response.ResponseHandler;
import cinema_backend.api_movie_system.service.FileStorageService;
import java.time.Duration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/files")
public class FileController {

    private final FileStorageService fileStorageService;

    public FileController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<Object> upload(@RequestParam("file") MultipartFile file,
                                         @RequestParam(value = "ownerType", required = false) String ownerType,
                                         @RequestParam(value = "ownerId", required = false) Integer ownerId) {
        FileEntity savedFile = fileStorageService.uploadFile(file, ownerType, ownerId);
        return ResponseHandler.responseBuilder("File uploaded successfully", HttpStatus.OK, savedFile);
    }

    @GetMapping("/{fileId}/url")
    public ResponseEntity<Object> getUrl(@PathVariable Integer fileId,
                                         @RequestParam(defaultValue = "300") long ttlSeconds) {
        String signedUrl = fileStorageService.getSignedUrl(fileId, Duration.ofSeconds(ttlSeconds));
        return ResponseHandler.responseBuilder("Signed URL generated", HttpStatus.OK, signedUrl);
    }

    @DeleteMapping("/{fileId}")
    public ResponseEntity<Object> delete(@PathVariable Integer fileId) {
        fileStorageService.deleteFile(fileId);
        return ResponseHandler.responseBuilder("File deleted successfully", HttpStatus.OK, null);
    }
}
