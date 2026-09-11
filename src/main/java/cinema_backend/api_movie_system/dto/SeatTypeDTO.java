package cinema_backend.api_movie_system.dto;

import cinema_backend.api_movie_system.models.SeatType;
import java.time.LocalDateTime;

public class SeatTypeDTO {
    private Integer id;
    private String name;
    private Double price;
    private LocalDateTime createdDate;

    public SeatTypeDTO() {}

    public SeatTypeDTO(SeatType seatType) {
        this.id = seatType.getId();
        this.name = seatType.getName();
        this.price = seatType.getPrice();
        this.createdDate = seatType.getCreatedDate();
    }

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }
}