package cinema_backend.api_movie_system.repository;

import cinema_backend.api_movie_system.models.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Integer> {
}
