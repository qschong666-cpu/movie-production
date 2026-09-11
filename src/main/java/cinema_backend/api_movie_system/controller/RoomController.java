// package cinema_backend.api_movie_system.controller;

// import cinema_backend.api_movie_system.dto.CreateRoomRequest;
// import cinema_backend.api_movie_system.dto.RoomDTO;
// import cinema_backend.api_movie_system.response.ResponseHandler;
// import cinema_backend.api_movie_system.service.RoomService;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.access.prepost.PreAuthorize;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/api/rooms")
// public class RoomController {

//     private final RoomService roomService;

//     public RoomController(RoomService roomService) {
//         this.roomService = roomService;
//     }

//     @GetMapping("/branch/{branchId}")
//     public ResponseEntity<Object> getRoomsByBranch(@PathVariable Integer branchId) {
//         List<RoomDTO> rooms = roomService.getRoomsByBranch(branchId);
//         return ResponseHandler.success("Rooms retrieved successfully", rooms);
//     }

//     @GetMapping("/{id}")
//     public ResponseEntity<Object> getRoomById(@PathVariable Integer id) {
//         RoomDTO room = roomService.getRoomById(id);
//         return ResponseHandler.success("Room retrieved successfully", room);
//     }

//     @PostMapping("/create")
//     @PreAuthorize("hasAnyAuthority('GLOBAL_ADMIN', 'SUB_ADMIN', 'BRANCH_MANAGER')")
//     public ResponseEntity<Object> createRoom(@RequestBody CreateRoomRequest request) {
//         RoomDTO created = roomService.createRoom(request);
//         return ResponseHandler.success("Room created successfully", created);
//     }

//     @PutMapping("/{id}")
//     @PreAuthorize("hasAnyAuthority('GLOBAL_ADMIN', 'SUB_ADMIN', 'BRANCH_MANAGER')")
//     public ResponseEntity<Object> updateRoom(@PathVariable Integer id, @RequestBody CreateRoomRequest request) {
//         RoomDTO updated = roomService.updateRoom(id, request);
//         return ResponseHandler.success("Room updated successfully", updated);
//     }

//     @DeleteMapping("/{id}")
//     @PreAuthorize("hasAnyAuthority('GLOBAL_ADMIN', 'SUB_ADMIN', 'BRANCH_MANAGER')")
//     public ResponseEntity<Object> deleteRoom(@PathVariable Integer id) {
//         roomService.deleteRoom(id);
//         return ResponseHandler.success("Room deleted successfully", null);
//     }

//     @PostMapping("/{roomId}/seats")
//     @PreAuthorize("hasAnyAuthority('GLOBAL_ADMIN', 'SUB_ADMIN', 'BRANCH_MANAGER')")
//     public ResponseEntity<Object> addSeatsToRoom(@PathVariable Integer roomId, @RequestBody List<SeatDTO> seats) {
//         List<SeatDTO> createdSeats = roomService.addSeatsToRoom(roomId, seats);
//         return ResponseHandler.success("Seats added successfully", createdSeats);
//     }

//     @PutMapping("/{roomId}/seats/{seatId}")
//     @PreAuthorize("hasAnyAuthority('GLOBAL_ADMIN', 'SUB_ADMIN', 'BRANCH_MANAGER')")
//     public ResponseEntity<Object> updateSeatInRoom(@PathVariable Integer roomId, 
//                                                      @PathVariable Integer seatId, 
//                                                      @RequestBody SeatDTO seatDTO) {
//         SeatDTO updated = roomService.updateSeatInRoom(roomId, seatId, seatDTO);
//         return ResponseHandler.success("Seat updated successfully", updated);
//     }

//     @DeleteMapping("/{roomId}/seats/{seatId}")
//     @PreAuthorize("hasAnyAuthority('GLOBAL_ADMIN', 'SUB_ADMIN', 'BRANCH_MANAGER')")
//     public ResponseEntity<Object> deleteSeatFromRoom(@PathVariable Integer roomId, @PathVariable Integer seatId) {
//         roomService.deleteSeatFromRoom(roomId, seatId);
//         return ResponseHandler.success("Seat deleted successfully", null);
//     }
// }