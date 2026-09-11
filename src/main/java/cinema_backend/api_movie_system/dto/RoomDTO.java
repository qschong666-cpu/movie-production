package cinema_backend.api_movie_system.dto;

import cinema_backend.api_movie_system.models.Room;
import cinema_backend.api_movie_system.models.Seat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RoomDTO {
    private Integer id;
    private String name;
    private Integer roomNumber;
    private Integer totalColumns;
    private Integer totalRows;
    private Boolean status;
    private Boolean isDeleted;
    private Integer roomTypeId;
    private String roomTypeName;
    private Integer branchId;
    private String branchName;
    private List<SeatDTO> seats;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private Integer createdBy;
    private Integer updatedBy;

    public RoomDTO() {
        this.seats = new ArrayList<>();
    }

    public RoomDTO(Room room) {
        this.id = room.getId();
        this.name = room.getName();
        this.roomNumber = room.getRoomNumber();
        this.totalColumns = room.getTotalColumns();
        this.totalRows = room.getTotalRows();
        this.status = room.getStatus();
        this.isDeleted = room.getIsDeleted();
        this.createdDate = room.getCreatedDate();
        this.updatedDate = room.getUpdatedDate();
        this.createdBy = room.getCreatedBy();
        this.updatedBy = room.getUpdatedBy();
        this.seats = new ArrayList<>();
        
        if (room.getRoomType() != null) {
            this.roomTypeId = room.getRoomType().getId();
            this.roomTypeName = room.getRoomType().getName();
        }
        if (room.getBranch() != null) {
            this.branchId = room.getBranch().getId();
            this.branchName = room.getBranch().getName();
        }
        if (room.getSeats() != null) {
            for (Seat seat : room.getSeats()) {
                this.seats.add(new SeatDTO(seat));
            }
        }
    }

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getRoomNumber() { return roomNumber; }
    public void setRoomNumber(Integer roomNumber) { this.roomNumber = roomNumber; }

    public Integer getTotalColumns() { return totalColumns; }
    public void setTotalColumns(Integer totalColumns) { this.totalColumns = totalColumns; }

    public Integer getTotalRows() { return totalRows; }
    public void setTotalRows(Integer totalRows) { this.totalRows = totalRows; }

    public Boolean getStatus() { return status; }
    public void setStatus(Boolean status) { this.status = status; }

    public Boolean getIsDeleted() { return isDeleted; }
    public void setIsDeleted(Boolean isDeleted) { this.isDeleted = isDeleted; }

    public Integer getRoomTypeId() { return roomTypeId; }
    public void setRoomTypeId(Integer roomTypeId) { this.roomTypeId = roomTypeId; }

    public String getRoomTypeName() { return roomTypeName; }
    public void setRoomTypeName(String roomTypeName) { this.roomTypeName = roomTypeName; }

    public Integer getBranchId() { return branchId; }
    public void setBranchId(Integer branchId) { this.branchId = branchId; }

    public String getBranchName() { return branchName; }
    public void setBranchName(String branchName) { this.branchName = branchName; }

    public List<SeatDTO> getSeats() { return seats; }
    public void setSeats(List<SeatDTO> seats) { this.seats = seats; }

    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }

    public LocalDateTime getUpdatedDate() { return updatedDate; }
    public void setUpdatedDate(LocalDateTime updatedDate) { this.updatedDate = updatedDate; }

    public Integer getCreatedBy() { return createdBy; }
    public void setCreatedBy(Integer createdBy) { this.createdBy = createdBy; }

    public Integer getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(Integer updatedBy) { this.updatedBy = updatedBy; }
}