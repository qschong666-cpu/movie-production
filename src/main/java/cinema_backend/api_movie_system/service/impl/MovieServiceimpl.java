package cinema_backend.api_movie_system.service.impl;

import cinema_backend.api_movie_system.exception.MovieNotFoundException;
import cinema_backend.api_movie_system.models.Movie;
import cinema_backend.api_movie_system.repository.MovieRepository;
import cinema_backend.api_movie_system.service.MovieService;
import java.util.List;
import java.util.Objects;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MovieServiceimpl implements MovieService {

    private final MovieRepository movieRepository;

    public MovieServiceimpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public Movie createMovie(Movie movie) {
        movie.prepareForCreate();

        if (movie.getId() == null) {
            movie.setId(generateNextMovieId());
        }

        return movieRepository.save(movie);
    }

    @Override
    public Movie updateMovie(Movie movie) {
        Movie existingMovie = movieRepository.findById(movie.getId())
            .orElseThrow(() -> new MovieNotFoundException("Movie not found with ID: " + movie.getId()));

        if (movie.getIsDeleted() == null) {
            movie.setIsDeleted(existingMovie.getIsDeleted());
        }

        if (movie.getStatus() == null) {
            movie.setStatus(existingMovie.getStatus());
        }

        if (movie.getCreatedBy() == null) {
            movie.setCreatedBy(existingMovie.getCreatedBy());
        }

        return movieRepository.save(movie);
    }

    @Override
    public String deleteMovie(int movieId) {
        return softDeleteMovie(movieId);
    }

    @Override
    public String softDeleteMovie(int movieId) {
        Movie movie = movieRepository.findById(movieId)
            .orElseThrow(() -> new MovieNotFoundException("Movie not found with ID: " + movieId));

        movie.setIsDeleted(true);
        movie.setStatus(false);
        movieRepository.save(movie);
        return "Success";
    }

    @Override
    public Movie getMovie(int movieId) {
        Movie movie = movieRepository.findById(movieId)
            .orElseThrow(() -> new MovieNotFoundException("Movie not found with ID: " + movieId));

        if (Boolean.TRUE.equals(movie.getIsDeleted())) {
            throw new MovieNotFoundException("Movie not found with ID: " + movieId);
        }

        return movie;
    }

    @Override
    public List<Movie> getAllMovies() {
        return movieRepository.findByIsDeletedFalse();
    }

    @Override
    public Page<Movie> getMoviesPage(String search, String status, Pageable pageable) {
        Boolean statusFilter = normalizeStatusFilter(status);

        if (search != null && !search.isBlank() && statusFilter != null) {
            return movieRepository.findByNameContainingIgnoreCaseAndStatusAndIsDeletedFalse(search, statusFilter, pageable);
        }

        if (search != null && !search.isBlank()) {
            return movieRepository.findByNameContainingIgnoreCaseAndIsDeletedFalse(search, pageable);
        }

        if (statusFilter != null) {
            return movieRepository.findByStatusAndIsDeletedFalse(statusFilter, pageable);
        }

        return movieRepository.findByIsDeletedFalse(pageable);
    }

    private Boolean normalizeStatusFilter(String status) {
        if (status == null || status.isBlank()) {
            return null;
        }

        String normalizedStatus = status.trim().toUpperCase();

        if ("ACTIVE".equals(normalizedStatus)) {
            return true;
        }

        if ("INACTIVE".equals(normalizedStatus)) {
            return false;
        }

        return null;
    }

    private int generateNextMovieId() {
        return movieRepository.findAll().stream()
            .map(Movie::getId)
            .filter(Objects::nonNull)
            .mapToInt(Integer::intValue)
            .max()
            .orElse(0) + 1;
    }
}


