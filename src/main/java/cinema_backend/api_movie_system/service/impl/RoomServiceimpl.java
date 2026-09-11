// package cinema_backend.api_movie_system.service.impl;

// import cinema_backend.api_movie_system.exception.ResourceNotFoundException;
// import cinema_backend.api_movie_system.models.Branch;
// import cinema_backend.api_movie_system.models.Room;
// import cinema_backend.api_movie_system.models.RoomType;
// import cinema_backend.api_movie_system.repository.BranchRepository;
// import cinema_backend.api_movie_system.repository.RoomRepository;
// import cinema_backend.api_movie_system.repository.RoomTypeRepository;
// import cinema_backend.api_movie_system.service.RoomService;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

// import java.time.LocalDateTime;
// import java.util.List;

// @Service
// public class RoomServiceimpl implements RoomService {

//     private final RoomRepository roomRepository;
//     private final BranchRepository branchRepository;
//     private final RoomTypeRepository roomTypeRepository;

//     public RoomServiceimpl(RoomRepository roomRepository, BranchRepository branchRepository, 
//                            RoomTypeRepository roomTypeRepository) {
//         this.roomRepository = roomRepository;
//         this.branchRepository = branchRepository;
//         this.roomTypeRepository = roomTypeRepository;
//     }

//     @Override
//     public List<Room> getAllRooms() {
//         return roomRepository.findByIsDeletedFalse();
//     }

//     @Override
//     public List<Room> getRoomsByBranch(Integer branchId) {
//         return roomRepository.findByBranchIdAndIsDeletedFalse(branchId);
//     }

//     @Override
//     public Room getRoomById(Integer id) {
//         return roomRepository.findByIdAndIsDeletedFalse(id)
//                 .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + id));
//     }

//     @Override
//     @Transactional
//     public Room createRoom(Room room) {
//         // Validate branch exists
//         Branch branch = branchRepository.findById(room.getBranch().getId())
//                 .orElseThrow(() -> new ResourceNotFoundException("Branch not found"));
//         room.setBranch(branch);

//         // Validate room type exists
//         if (room.getRoomType() != null && room.getRoomType().getId() != null) {
//             RoomType roomType = roomTypeRepository.findById(room.getRoomType().getId())
//                     .orElseThrow(() -> new ResourceNotFoundException("Room type not found"));
//             room.setRoomType(roomType);
//         }

//         room.setCreatedDate(LocalDateTime.now());
//         room.setIsDeleted(false);
//         room.setStatus(true);
//         return roomRepository.save(room);
//     }

//     @Override
//     @Transactional
//     public Room updateRoom(Integer id, Room room) {
//         Room existing = getRoomById(id);
        
//         existing.setName(room.getName());
//         existing.setRoomNumber(room.getRoomNumber());
//         existing.setTotalColumns(room.getTotalColumns());
//         existing.setTotalRows(room.getTotalRows());
//         existing.setStatus(room.getStatus());
        
//         if (room.getRoomType() != null && room.getRoomType().getId() != null) {
//             RoomType roomType = roomTypeRepository.findById(room.getRoomType().getId())
//                     .orElseThrow(() -> new ResourceNotFoundException("Room type not found"));
//             existing.setRoomType(roomType);
//         }
        
//         if (room.getBranch() != null && room.getBranch().getId() != null) {
//             Branch branch = branchRepository.findById(room.getBranch().getId())
//                     .orElseThrow(() -> new ResourceNotFoundException("Branch not found"));
//             existing.setBranch(branch);
//         }
        
//         existing.setUpdatedDate(LocalDateTime.now());
//         return roomRepository.save(existing);
//     }

//     @Override
//     @Transactional
//     public void deleteRoom(Integer id) {
//         Room existing = getRoomById(id);
//         existing.setIsDeleted(true);
//         roomRepository.save(existing);
//     }
// }