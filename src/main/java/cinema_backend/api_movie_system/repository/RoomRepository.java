package cinema_backend.api_movie_system.repository;

import cinema_backend.api_movie_system.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {

    /**
     * Find all rooms that are not deleted
     */
    List<Room> findByIsDeletedFalse();

    /**
     * Find all active rooms (status = true and not deleted)
     */
    List<Room> findByStatusTrueAndIsDeletedFalse();

    /**
     * Find rooms by branch ID that are not deleted
     */
    List<Room> findByBranchIdAndIsDeletedFalse(Integer branchId);

    /**
     * Find active rooms by branch ID (status = true and not deleted)
     */
    List<Room> findByBranchIdAndStatusTrueAndIsDeletedFalse(Integer branchId);

    /**
     * Find a room by ID that is not deleted
     */
    Optional<Room> findByIdAndIsDeletedFalse(Integer id);

    /**
     * Find rooms by room type ID that are not deleted
     */
    List<Room> findByRoomTypeIdAndIsDeletedFalse(Integer roomTypeId);

    /**
     * Find rooms by branch ID and room number (for uniqueness validation)
     */
    Optional<Room> findByBranchIdAndRoomNumberAndIsDeletedFalse(Integer branchId, Integer roomNumber);

    /**
     * Check if a room number already exists in a branch
     */
    boolean existsByBranchIdAndRoomNumberAndIsDeletedFalse(Integer branchId, Integer roomNumber);

    /**
     * Find rooms with seat count information
     */
    @Query("SELECT r, COUNT(s) as seatCount FROM Room r " +
           "LEFT JOIN r.seats s " +
           "WHERE r.isDeleted = false " +
           "AND r.branch.id = :branchId " +
           "GROUP BY r.id")
    List<Object[]> findRoomsWithSeatCountByBranch(@Param("branchId") Integer branchId);

    /**
     * Find rooms that are available for showtime scheduling
     * (active, not deleted, and have seats)
     */
    @Query("SELECT r FROM Room r " +
           "WHERE r.isDeleted = false " +
           "AND r.status = true " +
           "AND r.branch.id = :branchId " +
           "AND (SELECT COUNT(s) FROM Seat s WHERE s.seatRoom.id = r.id AND s.status = true) > 0")
    List<Room> findAvailableRoomsForShowtime(@Param("branchId") Integer branchId);

    /**
     * Search rooms by name (partial match)
     */
    @Query("SELECT r FROM Room r WHERE r.isDeleted = false AND LOWER(r.name) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Room> searchRoomsByName(@Param("searchTerm") String searchTerm);

    /**
     * Find rooms created by a specific user
     */
    List<Room> findByCreatedByAndIsDeletedFalse(Integer createdBy);

    /**
     * Count total active rooms in a branch
     */
    @Query("SELECT COUNT(r) FROM Room r WHERE r.branch.id = :branchId AND r.status = true AND r.isDeleted = false")
    Long countActiveRoomsByBranch(@Param("branchId") Integer branchId);
}