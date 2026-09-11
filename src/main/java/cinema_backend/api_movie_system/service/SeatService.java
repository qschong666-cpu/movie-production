package cinema_backend.api_movie_system.service;

import cinema_backend.api_movie_system.models.Seat;
import cinema_backend.api_movie_system.models.SeatLayoutRequest;
import java.util.List;

public interface SeatService {
    List<Seat> getSeatsByRoom(Integer roomId);
    Seat getSeatById(Integer id);
    Seat createSeat(Seat seat);
    List<Seat> generateSeatsForRoom(Integer roomId, SeatLayoutRequest layoutRequest);
    Seat updateSeat(Integer id, Seat seat);
    void deleteSeat(Integer id);
    List<Seat> arrangeSeats(Integer roomId, List<Seat> seats);
}