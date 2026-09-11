package cinema_backend.api_movie_system.service.impl;

import cinema_backend.api_movie_system.exception.RoomNotFoundException;
import cinema_backend.api_movie_system.models.RoomType;
import cinema_backend.api_movie_system.repository.RoomTypeRepository;
import cinema_backend.api_movie_system.service.RoomTypeService;
import java.util.List;
import java.util.Objects;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RoomTypeServiceimpl implements RoomTypeService {

    private final RoomTypeRepository roomTypeRepository;

    public RoomTypeServiceimpl(RoomTypeRepository roomTypeRespository) {
        this.roomTypeRepository = roomTypeRespository;
    }

    @Override
    public RoomType createRoomType(RoomType roomType) {
        roomType.prepareForCreate();

        if (roomType.getId() == null) {
            roomType.setId(generateNextRoomTypeId());
        }

        return roomTypeRepository.save(roomType);
    }

    @Override
    public RoomType updateRoomType(RoomType roomType) {
        RoomType existingRoomType = roomTypeRepository.findById(roomType.getId())
                .orElseThrow(() -> new RoomNotFoundException("RoomType not found with id " + roomType.getId()));

        return roomTypeRepository.save(roomType);
    }

    @Override
    public String deletedRoomType(int roomTypeId) {
        return softDeletedRoomType(roomTypeId);
    }

    @Override
    public String softDeletedRoomType(int roomTypeId) {
        RoomType roomType = roomTypeRepository.findById(roomTypeId)
                .orElseThrow(() -> new RoomNotFoundException("RoomType not found with id " + roomTypeId));
        
        roomType.setIsDeleted(true);
        roomTypeRepository.save(roomType);
        return "Success";
    }

    @Override
    public RoomType getRoomType(int roomTypeId) {
        RoomType roomType = roomTypeRepository.findById(roomTypeId)
                .orElseThrow(() -> new RoomNotFoundException("RoomType not found with id " + roomTypeId));

        return roomType;
    }

    @Override
    public List<RoomType> getAllRoomTypes() {
        return roomTypeRepository.findByIsDeletedFalse();
    }

    @Override
    public Page<RoomType> getRoomTypesPages(String search, String status, Pageable pageable) {

        if (search != null && !search.isBlank()) {
            return roomTypeRepository.findByNameContainingIgnoreCaseAndIsDeletedFalse(search, pageable);
        }
        
        return roomTypeRepository.findByIsDeletedFalse(pageable);
    }



    private int generateNextRoomTypeId() {
        return roomTypeRepository.findByIsDeletedFalse().stream()
                .map(RoomType::getId)
                .filter(Objects::nonNull)
                .mapToInt(Integer::intValue)
                .max()
                .orElse(0) + 1;
    }
}