package cinema_backend.api_movie_system.repository;

import cinema_backend.api_movie_system.models.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Integer> {

    /**
     * Find all seats for a specific room that are active (status = true)
     */
    List<Seat> findBySeatRoomIdAndStatusTrue(Integer roomId);

    /**
     * Find all seats for a specific room (including inactive ones)
     */
    List<Seat> findBySeatRoomId(Integer roomId);

    /**
     * Find a seat by its room ID and seat number
     */
    Optional<Seat> findBySeatRoomIdAndSeatNumber(Integer roomId, String seatNumber);

    /**
     * Find all seats for a specific room, ordered by row label and column number
     */
    @Query("SELECT s FROM Seat s WHERE s.seatRoom.id = :roomId AND s.status = true ORDER BY s.rowLabel ASC, s.columnNumber ASC")
    List<Seat> findActiveSeatsByRoomOrdered(@Param("roomId") Integer roomId);

    /**
     * Count active seats in a room
     */
    @Query("SELECT COUNT(s) FROM Seat s WHERE s.seatRoom.id = :roomId AND s.status = true")
    Long countActiveSeatsByRoom(@Param("roomId") Integer roomId);

    /**
     * Find seats by seat type ID
     */
    List<Seat> findBySeatTypeIdAndStatusTrue(Integer seatTypeId);

    /**
     * Find seats by room ID and row label
     */
    List<Seat> findBySeatRoomIdAndRowLabelAndStatusTrue(Integer roomId, String rowLabel);

    /**
     * Check if a seat number already exists in a room
     */
    boolean existsBySeatRoomIdAndSeatNumber(Integer roomId, String seatNumber);

    /**
     * Delete all seats for a specific room
     */
    @Query("DELETE FROM Seat s WHERE s.seatRoom.id = :roomId")
    void deleteAllByRoomId(@Param("roomId") Integer roomId);
}