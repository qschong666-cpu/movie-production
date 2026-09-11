package cinema_backend.api_movie_system.service;


import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import cinema_backend.api_movie_system.models.RoomType;

public interface RoomTypeService {
   RoomType createRoomType(RoomType roomType);

   RoomType updateRoomType(RoomType roomType);

   String deletedRoomType(int roomTypeId);

   String softDeletedRoomType(int roomTypeId);

   RoomType getRoomType(int roomTypeId);

   List<RoomType> getAllRoomTypes();

   Page<RoomType> getRoomTypesPages(String search,String status,Pageable pageable);
}