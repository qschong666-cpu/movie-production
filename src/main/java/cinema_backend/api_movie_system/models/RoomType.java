package cinema_backend.api_movie_system.models;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "ref_room_type")
public class RoomType {

    @Id
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Double price;

    @Column(name = "create_date", updatable = false)
    private LocalDateTime createDate;

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    @OneToMany(mappedBy = "roomType")
    private List<Room> rooms;

    public RoomType() {}

    public RoomType(Integer id, String name, Double price, LocalDateTime createDate) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.createDate = createDate;
    }

    @PrePersist
    public void prePersist(){
        if(this.createDate == null){
            this.createDate = LocalDateTime.now();
        }
    }

    public void prepareForCreate() {
        prePersist();
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public LocalDateTime getCreateDate() { return createDate; }
    public void setCreateDate(LocalDateTime createDate) { this.createDate = createDate; }

    public Boolean getIsDeleted(){ return this.isDeleted; }
    public void setIsDeleted(Boolean isDelete) { this.isDeleted = isDelete; }

    @JsonIgnore
    public List<Room> getRooms() { return rooms; }
    public void setRooms(List<Room> rooms) { this.rooms = rooms; }
}