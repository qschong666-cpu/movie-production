package cinema_backend.api_movie_system.repository;

import cinema_backend.api_movie_system.models.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity, Integer> {
}
