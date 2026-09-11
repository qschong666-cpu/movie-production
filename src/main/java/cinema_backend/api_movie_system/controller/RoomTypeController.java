package cinema_backend.api_movie_system.controller;


import cinema_backend.api_movie_system.models.Movie;
import cinema_backend.api_movie_system.models.RoomType;
import cinema_backend.api_movie_system.models.User;
import cinema_backend.api_movie_system.response.ResponseHandler;
import cinema_backend.api_movie_system.service.FileStorageService;
import java.time.Duration;
import cinema_backend.api_movie_system.service.RoomTypeService;
import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
@RequestMapping("/room-types")
public class RoomTypeController {

   private final RoomTypeService roomTypeService;

   public RoomTypeController(RoomTypeService roomTypeService) {
       this.roomTypeService = roomTypeService;
   }

   @PreAuthorize("hasAuthority('PERM_ROOM_TYPE_READ')")
   @GetMapping("{roomTypeId}")
   public ResponseEntity<Object> getRoomTypeDetails(@PathVariable("roomTypeId") int roomTypeId) {
       RoomType roomType = roomTypeService.getRoomType(roomTypeId);
       return ResponseHandler.responseBuilder("Successfully retrieved room type details", HttpStatus.OK, roomType);
   }

   @PreAuthorize("hasAuthority('PERM_ROOM_TYPE_READ')")
   @GetMapping()
   public List<RoomType> getAllRoomTypes() {
       return roomTypeService.getAllRoomTypes();
   }

   @PreAuthorize("hasAuthority('PERM_ROOM_TYPE_READ')")
    @GetMapping("/page")
    public Page<RoomType> getRoomTypesPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "createDate") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortOrder) {
        Sort.Direction direction = sortOrder.equalsIgnoreCase("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        return roomTypeService.getRoomTypesPages(search, status, pageable);
    }

    @PreAuthorize("hasAuthority('PERM_ROOM_TYPE_CREATE')")
    @PostMapping 
    public ResponseEntity<Object> createRoomTypeDetails(@Valid @RequestBody RoomType roomType,@AuthenticationPrincipal User user) {
        // roomType.setCreatedBy(user.getId());
        RoomType createdRoomType = roomTypeService.createRoomType(roomType);
        String message = createdRoomType.getName() + " created successfully";
        return ResponseHandler.responseBuilder(message, HttpStatus.CREATED, createdRoomType);
    }

    @PreAuthorize("hasAuthority('PERM_ROOM_TYPE_UPDATE')")
    @PutMapping
    public ResponseEntity<RoomType> updateRoomTypeDetails(@RequestBody RoomType roomtype){
        RoomType updatedRoomType = roomTypeService.updateRoomType(roomtype);
        return ResponseEntity.ok(updatedRoomType);
    }

    @PreAuthorize("hasAuthority('PERM_ROOM_TYPE_DELETE')")
    @DeleteMapping("{roomTypeId}")
    public  ResponseEntity<Object> deleteMovieDetails(@PathVariable("roomTypeId") int roomTypeId) {
        roomTypeService.softDeletedRoomType(roomTypeId);
        String message = roomTypeId + " Delete room type successfully";

        return ResponseHandler.responseBuilder(message, HttpStatus.OK, roomTypeId);
    }


}