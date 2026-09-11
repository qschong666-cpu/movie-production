package cinema_backend.api_movie_system.dto;

import cinema_backend.api_movie_system.models.RoomType;
import java.time.LocalDateTime;

public class RoomTypeDTO {
    private Integer id;
    private String name;
    private Double price;
    private LocalDateTime createDate;

    public RoomTypeDTO() {}

    public RoomTypeDTO(RoomType roomType) {
        this.id = roomType.getId();
        this.name = roomType.getName();
        this.price = roomType.getPrice();
        this.createDate = roomType.getCreateDate();
    }

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public LocalDateTime getCreateDate() { return createDate; }
    public void setCreateDate(LocalDateTime createDate) { this.createDate = createDate; }
}