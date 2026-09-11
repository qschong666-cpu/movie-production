package cinema_backend.api_movie_system.service;

import cinema_backend.api_movie_system.models.Room;
import java.util.List;

public interface RoomService {
    List<Room> getAllRooms();
    List<Room> getRoomsByBranch(Integer branchId);
    Room getRoomById(Integer id);
    Room createRoom(Room room);
    Room updateRoom(Integer id, Room room);
    void deleteRoom(Integer id);
}