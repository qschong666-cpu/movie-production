package cinema_backend.api_movie_system.repository;
import cinema_backend.api_movie_system.models.Country;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Integer> {
  
}