
package cinema_backend.api_movie_system.repository;
import cinema_backend.api_movie_system.models.SeatType;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatTypeRepository extends JpaRepository<SeatType, Integer> {
    Page<SeatType> findByNameContainingIgnoreCaseAndIsDeletedFalse(String name, Pageable pageable);
    List<SeatType> findByIsDeletedFalse();
    Page<SeatType> findByIsDeletedFalse(Pageable pageable);
}