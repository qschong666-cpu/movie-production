package cinema_backend.api_movie_system.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "tbl_city")
public class City {
    @Id
    private Integer id;
    private String name;
    
    @ManyToOne
    @JoinColumn(name="state_id")
    private State state;

    @OneToMany(mappedBy="city")
    @JsonIgnore
    private List<Branch> branches;
    
    public City(){}

    public City(Integer id, String name, State state) {
        this.id = id;
        this.name = name;
        this.state = state;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
    
}
