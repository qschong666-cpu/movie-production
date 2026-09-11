package cinema_backend.api_movie_system.repository;
import cinema_backend.api_movie_system.models.State;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateRepository extends JpaRepository<State, Integer> {
	List<State> findByCountry_IdOrderByNameAsc(Integer countryId);
}