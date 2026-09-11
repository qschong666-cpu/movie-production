// package cinema_backend.api_movie_system.controller;

// import cinema_backend.api_movie_system.models.Seat;
// import cinema_backend.api_movie_system.models.SeatLayoutRequest;
// import cinema_backend.api_movie_system.response.ResponseHandler;
// import cinema_backend.api_movie_system.service.SeatService;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.access.prepost.PreAuthorize;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/api/seats")
// public class SeatController {

//     private final SeatService seatService;

//     public SeatController(SeatService seatService) {
//         this.seatService = seatService;
//     }

//     @GetMapping("/room/{roomId}")
//     public ResponseEntity<Object> getSeatsByRoom(@PathVariable Integer roomId) {
//         List<Seat> seats = seatService.getSeatsByRoom(roomId);
//         return ResponseHandler.success(seats, "Seats retrieved successfully");
//     }

//     @GetMapping("/{id}")
//     public ResponseEntity<Object> getSeatById(@PathVariable Integer id) {
//         Seat seat = seatService.getSeatById(id);
//         return ResponseHandler.success(seat, "Seat retrieved successfully");
//     }

//     @PostMapping
//     @PreAuthorize("hasAnyAuthority('GLOBAL_ADMIN', 'SUB_ADMIN', 'BRANCH_MANAGER')")
//     public ResponseEntity<Object> createSeat(@RequestBody Seat seat) {
//         Seat created = seatService.createSeat(seat);
//         return ResponseHandler.success(created, "Seat created successfully", HttpStatus.CREATED);
//     }

//     @PostMapping("/room/{roomId}/generate")
//     @PreAuthorize("hasAnyAuthority('GLOBAL_ADMIN', 'SUB_ADMIN', 'BRANCH_MANAGER')")
//     public ResponseEntity<Object> generateSeatsForRoom(@PathVariable Integer roomId, 
//                                                         @RequestBody SeatLayoutRequest layoutRequest) {
//         List<Seat> seats = seatService.generateSeatsForRoom(roomId, layoutRequest);
//         return ResponseHandler.success(seats, "Seats generated successfully", HttpStatus.CREATED);
//     }

//     @PutMapping("/{id}")
//     @PreAuthorize("hasAnyAuthority('GLOBAL_ADMIN', 'SUB_ADMIN', 'BRANCH_MANAGER')")
//     public ResponseEntity<Object> updateSeat(@PathVariable Integer id, @RequestBody Seat seat) {
//         Seat updated = seatService.updateSeat(id, seat);
//         return ResponseHandler.success(updated, "Seat updated successfully");
//     }

//     @DeleteMapping("/{id}")
//     @PreAuthorize("hasAnyAuthority('GLOBAL_ADMIN', 'SUB_ADMIN', 'BRANCH_MANAGER')")
//     public ResponseEntity<Object> deleteSeat(@PathVariable Integer id) {
//         seatService.deleteSeat(id);
//         return ResponseHandler.success(null, "Seat deleted successfully");
//     }

//     @PostMapping("/room/{roomId}/arrange")
//     @PreAuthorize("hasAnyAuthority('GLOBAL_ADMIN', 'SUB_ADMIN', 'BRANCH_MANAGER')")
//     public ResponseEntity<Object> arrangeSeats(@PathVariable Integer roomId, 
//                                                 @RequestBody List<Seat> seats) {
//         List<Seat> arranged = seatService.arrangeSeats(roomId, seats);
//         return ResponseHandler.success(arranged, "Seats arranged successfully");
//     }
// }