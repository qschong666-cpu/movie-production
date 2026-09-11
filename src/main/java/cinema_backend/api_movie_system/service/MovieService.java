package cinema_backend.api_movie_system.service;

import cinema_backend.api_movie_system.models.Movie;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MovieService {
    Movie createMovie(Movie movie);

    Movie updateMovie(Movie movie);

    String deleteMovie(int movieId);

    String softDeleteMovie(int movieId);

    Movie getMovie(int movieId);

    List<Movie> getAllMovies();

    Page<Movie> getMoviesPage(String search, String status, Pageable pageable);
}
