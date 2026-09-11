package cinema_backend.api_movie_system.service.impl;
import cinema_backend.api_movie_system.repository.CityRepository;
import cinema_backend.api_movie_system.repository.CountryRepository;
import cinema_backend.api_movie_system.repository.StateRepository;
import cinema_backend.api_movie_system.service.LocationService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LocationServiceimpl implements LocationService {

    private final CountryRepository countryRepository;
    private final StateRepository stateRepository;
    private final CityRepository cityRepository;

    public LocationServiceimpl(CountryRepository countryRepository, StateRepository stateRepository, CityRepository cityRepository) {
        this.countryRepository = countryRepository;
        this.stateRepository = stateRepository;
        this.cityRepository = cityRepository;
    }

    @Override
    public List<CountryOption> getCountries() {
       return countryRepository.findAll().stream()
                .sorted((left, right) -> left.getName().compareToIgnoreCase(right.getName()))
                .map(country -> new CountryOption(country.getId(), country.getName()))
                .toList();
    }

    @Override
    public List<LocationOption> getStates(Integer countryId) {
        return stateRepository.findByCountry_IdOrderByNameAsc(countryId).stream()
                .map(state -> new LocationOption(state.getId(), state.getName()))
                .toList();
    }

    @Override
    public List<LocationOption> getCities(Integer stateId) {
        return cityRepository.findByState_IdOrderByNameAsc(stateId).stream()
                .map(city -> new LocationOption(city.getId(), city.getName()))
                .toList();
    }

  
}
