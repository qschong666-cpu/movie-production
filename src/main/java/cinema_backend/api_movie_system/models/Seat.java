package cinema_backend.api_movie_system.models;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_seats")
public class Seat {

    @Id
    private Integer id;

    @Column(name = "row_label")
    private String rowLabel;

    @Column(name = "column_number")
    private Integer columnNumber;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "seat_number")
    private String seatNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")  // This is the foreign key column in database
    private Room seatRoom;  // This is the field name in Java

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_types_id")
    private SeatType seatType;

    public Seat() {}

    public Seat(String rowLabel, Integer columnNumber, String seatNumber, Boolean status) {
        this.rowLabel = rowLabel;
        this.columnNumber = columnNumber;
        this.seatNumber = seatNumber;
        this.status = status;
    }

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getRowLabel() { return rowLabel; }
    public void setRowLabel(String rowLabel) { this.rowLabel = rowLabel; }

    public Integer getColumnNumber() { return columnNumber; }
    public void setColumnNumber(Integer columnNumber) { this.columnNumber = columnNumber; }

    public Boolean getStatus() { return status; }
    public void setStatus(Boolean status) { this.status = status; }

    public String getSeatNumber() { return seatNumber; }
    public void setSeatNumber(String seatNumber) { this.seatNumber = seatNumber; }

    public Room getSeatRoom() { return seatRoom; }
    public void setSeatRoom(Room seatRoom) { this.seatRoom = seatRoom; }

    public SeatType getSeatType() { return seatType; }
    public void setSeatType(SeatType seatType) { this.seatType = seatType; }
}