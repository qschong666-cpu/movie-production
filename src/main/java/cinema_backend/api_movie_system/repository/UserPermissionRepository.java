package cinema_backend.api_movie_system.repository;

import cinema_backend.api_movie_system.models.UserPermission;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPermissionRepository extends JpaRepository<UserPermission, Integer> {
    @EntityGraph(attributePaths = "permission")
    List<UserPermission> findByUser_Id(Integer userId);
}
