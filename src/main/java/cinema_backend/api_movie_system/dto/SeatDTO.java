package cinema_backend.api_movie_system.dto;

import cinema_backend.api_movie_system.models.Seat;

public class SeatDTO {
    private Integer id;
    private String rowLabel;
    private Integer columnNumber;
    private String seatNumber;
    private Boolean status;
    private Integer seatTypeId;
    private String seatTypeName;
    private Double seatTypePrice;
    private Integer roomId;

    public SeatDTO() {}

    public SeatDTO(Seat seat) {
        this.id = seat.getId();
        this.rowLabel = seat.getRowLabel();
        this.columnNumber = seat.getColumnNumber();
        this.seatNumber = seat.getSeatNumber();
        this.status = seat.getStatus();
        
        if (seat.getSeatType() != null) {
            this.seatTypeId = seat.getSeatType().getId();
            this.seatTypeName = seat.getSeatType().getName();
            this.seatTypePrice = seat.getSeatType().getPrice();
        }
        if (seat.getSeatRoom() != null) {
            this.roomId = seat.getSeatRoom().getId();
        }
    }

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getRowLabel() { return rowLabel; }
    public void setRowLabel(String rowLabel) { this.rowLabel = rowLabel; }

    public Integer getColumnNumber() { return columnNumber; }
    public void setColumnNumber(Integer columnNumber) { this.columnNumber = columnNumber; }

    public String getSeatNumber() { return seatNumber; }
    public void setSeatNumber(String seatNumber) { this.seatNumber = seatNumber; }

    public Boolean getStatus() { return status; }
    public void setStatus(Boolean status) { this.status = status; }

    public Integer getSeatTypeId() { return seatTypeId; }
    public void setSeatTypeId(Integer seatTypeId) { this.seatTypeId = seatTypeId; }

    public String getSeatTypeName() { return seatTypeName; }
    public void setSeatTypeName(String seatTypeName) { this.seatTypeName = seatTypeName; }

    public Double getSeatTypePrice() { return seatTypePrice; }
    public void setSeatTypePrice(Double seatTypePrice) { this.seatTypePrice = seatTypePrice; }

    public Integer getRoomId() { return roomId; }
    public void setRoomId(Integer roomId) { this.roomId = roomId; }
}