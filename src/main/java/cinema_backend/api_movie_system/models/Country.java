package cinema_backend.api_movie_system.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "tbl_country")
public class Country {
    @Id
    private Integer id;
    private String name;
    private String code;
    private String description;

    @OneToMany(mappedBy="country")
    @JsonIgnore
    private List<State> states;

    public Country(){}

    public Country(Integer id, String name, String code, String description, List<State> states) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.description = description;
        this.states =states;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<State> getStates() {
        return states;
    }

    public void setStates(List<State> states) {
        this.states = states;
    }
    
}
