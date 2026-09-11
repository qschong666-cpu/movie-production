package cinema_backend.api_movie_system.controller;


import cinema_backend.api_movie_system.service.LocationService;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/locations")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/countries")
    public List<LocationService.CountryOption> getCountries() {
        return locationService.getCountries();
    }

    @GetMapping("/states")
    public List<LocationService.LocationOption> getStates(@RequestParam Integer countryId) {
        return locationService.getStates(countryId);
    }

    @GetMapping("/cities")
    public List<LocationService.LocationOption> getCities(@RequestParam Integer stateId) {
        return locationService.getCities(stateId);
    }

    
}