package cinema_backend.api_movie_system.repository;
import cinema_backend.api_movie_system.models.City;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Integer> {
	List<City> findByState_IdOrderByNameAsc(Integer stateId);
}