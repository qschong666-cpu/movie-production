package cinema_backend.api_movie_system.models;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "ref_seat_types")
public class SeatType {

    @Id
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Double price;

    @Column(name = "created_date",updatable = false)
    private LocalDateTime createdDate;
    
    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    @OneToMany(mappedBy = "seatType")
    private List<Seat> seats;

    public SeatType() {}

    public SeatType(Integer id ,String name, Double price,LocalDateTime createDate){
        this.id = id;
        this.name = name;
        this.price = price;
        this.createdDate = createDate;
    }

    @PrePersist
    public void prePersist(){
        if(this.createdDate == null){
            this.createdDate = LocalDateTime.now();
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

    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }

    public Boolean getIsDeleted(){ return isDeleted;}
    public void setIsDeleted(Boolean isDelete) {this.isDeleted = isDelete;}

    @JsonIgnore
    public List<Seat> getSeats() { return seats; }
    public void setSeats(List<Seat> seats) { this.seats = seats; }
}