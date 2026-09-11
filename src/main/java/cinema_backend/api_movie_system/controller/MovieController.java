package cinema_backend.api_movie_system.controller;

import cinema_backend.api_movie_system.models.FileEntity;
import cinema_backend.api_movie_system.models.Movie;
import cinema_backend.api_movie_system.models.User;
import cinema_backend.api_movie_system.response.ResponseHandler;
import cinema_backend.api_movie_system.service.FileStorageService;
import java.time.Duration;
import cinema_backend.api_movie_system.service.MovieService;
import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/movie")

public class MovieController {

    private final MovieService movieService;
    private final FileStorageService fileStorageService;

    public MovieController(MovieService movieService, FileStorageService fileStorageService) {
        this.movieService = movieService;
        this.fileStorageService = fileStorageService;
    }

    @PreAuthorize("hasAuthority('PERM_MOVIE_READ')")
    @GetMapping("{movieId}")
    public ResponseEntity<Object> getMovieDetails(@PathVariable("movieId") int movieId) {
        Movie movie = movieService.getMovie(movieId);
        return ResponseHandler.responseBuilder("Request completed successfully", HttpStatus.OK, toMovieResponse(movie));
    }

    @PreAuthorize("hasAuthority('PERM_MOVIE_READ')")
    @GetMapping()
    public List<Movie> getAllMovieDetails() {
        return movieService.getAllMovies();
    }

    @PreAuthorize("hasAuthority('PERM_MOVIE_READ')")
    @GetMapping("/page")
    public Page<Movie> getMoviesPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "createDate") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortOrder) {
        Sort.Direction direction = sortOrder.equalsIgnoreCase("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        return movieService.getMoviesPage(search, status, pageable);
    }

    @PreAuthorize("hasAuthority('PERM_MOVIE_CREATE')")
    @PostMapping
    public ResponseEntity<Object> createMovieDetails(@Valid @RequestBody Movie movie,
            @AuthenticationPrincipal User user) {
        movie.setCreatedBy(user.getId());
        Movie createdMovie = movieService.createMovie(movie);

        String message = createdMovie.getName() + " movie created successfully";

        return ResponseHandler.responseBuilder(message, HttpStatus.CREATED, createdMovie);
    }

    @PreAuthorize("hasAuthority('PERM_MOVIE_CREATE')")
    @PostMapping(value = "/with-file", consumes = { "multipart/form-data" })
    public ResponseEntity<Movie> createMovieWithFile(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("duration") int duration,
            @RequestParam(value = "status", required = false, defaultValue = "true") boolean status,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @AuthenticationPrincipal User user) {
        Movie movie = new Movie();
        movie.setName(name);
        movie.setDescription(description);
        movie.setDuration(duration);
        movie.setStatus(status);
        movie.setCreatedBy(user.getId());

        if (file != null && !file.isEmpty()) {
            FileEntity savedFile = fileStorageService.uploadFile(file, "MOVIE", null);
            movie.setFile(savedFile);
        }

        Movie createdMovie = movieService.createMovie(movie);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMovie);
    }

    @PreAuthorize("hasAuthority('PERM_MOVIE_UPDATE')")
    @PutMapping
    public ResponseEntity<Movie> updateMovieDetails(@RequestBody Movie movie) {
        Movie updatedMovie = movieService.updateMovie(movie);
        return ResponseEntity.ok(updatedMovie);
    }

    @PreAuthorize("hasAuthority('PERM_MOVIE_UPDATE')")
    @PutMapping(value = "/with-file", consumes = { "multipart/form-data" })
    public ResponseEntity<Movie> updateMovieWithFile(
            @RequestParam("id") int id,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("duration") int duration,
            @RequestParam(value = "status", required = false, defaultValue = "true") boolean status,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        Movie movie = movieService.getMovie(id);
        movie.setName(name);
        movie.setDescription(description);
        movie.setDuration(duration);
        movie.setStatus(status);

        if (file != null && !file.isEmpty()) {
            FileEntity savedFile = fileStorageService.uploadFile(file, "MOVIE", id);
            movie.setFile(savedFile);
        }

        Movie updatedMovie = movieService.updateMovie(movie);
        return ResponseEntity.ok(updatedMovie);
    }

    @PreAuthorize("hasAuthority('PERM_MOVIE_DELETE')")
    @DeleteMapping("{movieId}")
    public  ResponseEntity<Object> deleteMovieDetails(@PathVariable("movieId") int movieId) {
        movieService.softDeleteMovie(movieId);
        String message =  " movie deleted successfully";

        return ResponseHandler.responseBuilder(message, HttpStatus.OK, movieId);
    }

    private Map<String, Object> toMovieResponse(Movie movie) {
        Map<String, Object> response = new HashMap<>();
        response.put("id", movie.getId());
        response.put("name", movie.getName());
        response.put("description", movie.getDescription());
        response.put("duration", movie.getDuration());
        response.put("createDate", movie.getCreateDate());
        response.put("status", movie.getStatus());
        response.put("createdBy", movie.getCreatedBy());

        if (movie.getFile() != null) {
            Map<String, Object> fileData = new HashMap<>();
            fileData.put("id", movie.getFile().getId());
            fileData.put("fileUrl", resolveFileUrl(movie.getFile()));
            fileData.put("storagePath", movie.getFile().getStoragePath());
            fileData.put("mimeType", movie.getFile().getMimeType());
            fileData.put("sizeBytes", movie.getFile().getSizeBytes());
            response.put("file", fileData);
        }

        return response;
    }

    private String resolveFileUrl(FileEntity file) {
        if (file == null || file.getId() == null) {
            return null;
        }

        try {
            return fileStorageService.getSignedUrl(file.getId(), Duration.ofHours(12));
        } catch (Exception ignored) {
            return file.getFileUrl();
        }
    }
}
