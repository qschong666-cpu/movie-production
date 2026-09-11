package cinema_backend.api_movie_system.controller;


import cinema_backend.api_movie_system.models.SeatType;
import cinema_backend.api_movie_system.models.User;
import cinema_backend.api_movie_system.response.ResponseHandler;
import cinema_backend.api_movie_system.service.SeatTypeService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController 
@RequestMapping("/seat-types")
public class SeatTypeController {

   private final SeatTypeService seatTypeService;

   public SeatTypeController(SeatTypeService seatTypeService) {
       this.seatTypeService = seatTypeService;
   }

   @PreAuthorize("hasAuthority('PERM_SEAT_TYPE_READ')")
   @GetMapping("{seatTypeId}")
   public ResponseEntity<Object> getSeatTypeDetails(@PathVariable("seatTypeId") int seatTypeId) {
       SeatType seatType = seatTypeService.getSeatType(seatTypeId);
       return ResponseHandler.responseBuilder("Successfully retrieved seat type details", HttpStatus.OK, seatType);
   }

   @PreAuthorize("hasAuthority('PERM_SEAT_TYPE_READ')")
   @GetMapping()
   public List<SeatType> getAllSeatTypes() {
       return seatTypeService.getAllSeatTypes();
   }

   @PreAuthorize("hasAuthority('PERM_SEAT_TYPE_READ')")
    @GetMapping("/page")
    public Page<SeatType> getSeatTypesPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "createDate") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortOrder) {
        Sort.Direction direction = sortOrder.equalsIgnoreCase("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        return seatTypeService.getSeatTypesPages(search, status, pageable);
    }

    @PreAuthorize("hasAuthority('PERM_SEAT_TYPE_CREATE')")
    @PostMapping 
    public ResponseEntity<Object> createSeatTypeDetails(@Valid @RequestBody SeatType seatType,@AuthenticationPrincipal User user) {
        // seatType.setCreatedBy(user.getId());
        SeatType createdSeatType = seatTypeService.createSeatType(seatType);
        String message = createdSeatType.getName() + " created successfully";
        return ResponseHandler.responseBuilder(message, HttpStatus.CREATED, createdSeatType);
    }

    @PreAuthorize("hasAuthority('PERM_SEAT_TYPE_UPDATE')")
    @PutMapping
    public ResponseEntity<SeatType> updateSeatTypeDetails(@RequestBody SeatType seattype){
        SeatType updatedSeatType = seatTypeService.updateSeatType(seattype);
        return ResponseEntity.ok(updatedSeatType);
    }

    @PreAuthorize("hasAuthority('PERM_SEAT_TYPE_DELETE')")
    @DeleteMapping("{seatTypeId}")
    public  ResponseEntity<Object> deleteMovieDetails(@PathVariable("seatTypeId") int seatTypeId) {
        seatTypeService.softDeletedSeatType(seatTypeId);
        String message = seatTypeId + " Delete seat type successfully";

        return ResponseHandler.responseBuilder(message, HttpStatus.OK, seatTypeId);
    }



}