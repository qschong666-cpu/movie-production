package cinema_backend.api_movie_system.repository;

import cinema_backend.api_movie_system.models.Movie;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
    Page<Movie> findByNameContainingIgnoreCaseAndStatusAndIsDeletedFalse(String name, Boolean status, Pageable pageable);
    Page<Movie> findByNameContainingIgnoreCaseAndIsDeletedFalse(String name, Pageable pageable);
    Page<Movie> findByStatusAndIsDeletedFalse(Boolean status, Pageable pageable);
    Page<Movie> findByIsDeletedFalse(Pageable pageable);
    List<Movie> findByIsDeletedFalse();
}
