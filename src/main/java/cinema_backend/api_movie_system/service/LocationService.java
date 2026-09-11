package cinema_backend.api_movie_system.service;
import java.util.List;
public interface LocationService {
    
    List<CountryOption> getCountries();
    List<LocationOption> getStates(Integer countryId);
    List<LocationOption> getCities(Integer stateId); 
    
    record CountryOption(Integer id, String name){}
    record LocationOption(Integer id, String name){}
}
