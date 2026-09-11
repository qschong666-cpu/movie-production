package cinema_backend.api_movie_system.service.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cinema_backend.api_movie_system.models.Movie;
import cinema_backend.api_movie_system.repository.MovieRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class MovieServiceimplTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieServiceimpl movieService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createMovie_shouldAssignIdWhenMissing() {
        Movie movie = new Movie();
        movie.setName("Inception");
        movie.setDescription("Sci-fi thriller");
        movie.setDuration(148);

        when(movieRepository.save(any(Movie.class))).thenAnswer(invocation -> {
            Movie savedMovie = invocation.getArgument(0);
            savedMovie.setId(1);
            return savedMovie;
        });

        movieService.createMovie(movie);

        ArgumentCaptor<Movie> movieCaptor = ArgumentCaptor.forClass(Movie.class);
        verify(movieRepository).save(movieCaptor.capture());

        assertNotNull(movieCaptor.getValue().getId());
    }

    @Test
    void updateMovie_shouldPreserveExistingSoftDeleteFlagWhenIncomingValueIsNull() {
        Movie existingMovie = new Movie();
        existingMovie.setId(1);
        existingMovie.setName("Interstellar");
        existingMovie.setDescription("Sci-fi");
        existingMovie.setDuration(169);
        existingMovie.setStatus(true);
        existingMovie.setIsDeleted(true);

        Movie incomingMovie = new Movie();
        incomingMovie.setId(1);
        incomingMovie.setName("Updated title");
        incomingMovie.setDescription("Updated description");
        incomingMovie.setDuration(180);
        incomingMovie.setStatus(false);
        incomingMovie.setIsDeleted(null);

        when(movieRepository.findById(1)).thenReturn(Optional.of(existingMovie));
        when(movieRepository.save(any(Movie.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Movie result = movieService.updateMovie(incomingMovie);

        ArgumentCaptor<Movie> movieCaptor = ArgumentCaptor.forClass(Movie.class);
        verify(movieRepository).save(movieCaptor.capture());

        assertTrue("Updated title".equals(result.getName()));
        assertTrue(movieCaptor.getValue().getIsDeleted());
        assertTrue(movieCaptor.getValue().getStatus() != null && !movieCaptor.getValue().getStatus());
    }

    @Test
    void softDeleteMovie_shouldMarkMovieAsDeletedAndInactive() {
        Movie movie = new Movie();
        movie.setId(1);
        movie.setName("Interstellar");
        movie.setStatus(true);
        movie.setIsDeleted(false);

        when(movieRepository.findById(1)).thenReturn(Optional.of(movie));
        when(movieRepository.save(any(Movie.class))).thenAnswer(invocation -> invocation.getArgument(0));

        String result = movieService.softDeleteMovie(1);

        ArgumentCaptor<Movie> movieCaptor = ArgumentCaptor.forClass(Movie.class);
        verify(movieRepository).save(movieCaptor.capture());

        assertTrue("Success".equals(result));
        assertTrue(movieCaptor.getValue().getIsDeleted());
        assertTrue(movieCaptor.getValue().getStatus() == null || !movieCaptor.getValue().getStatus());
    }
}
