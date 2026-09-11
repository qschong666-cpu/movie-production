package cinema_backend.api_movie_system.repository;

import cinema_backend.api_movie_system.models.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmailIgnoreCase(String email);

    @Override
    @EntityGraph(attributePaths = "role")
    Optional<User> findById(Integer id);

    boolean existsByEmail(String email);
      
    Optional<User> findByResetToken(String resetToken);

}
