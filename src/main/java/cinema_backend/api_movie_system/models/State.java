package cinema_backend.api_movie_system.models;
import jakarta.persistence.*;
import java.util.Objects;
import java.util.List;

@Entity
@Table(name = "tbl_state")
public class State {
    @Id
    private Integer id;
    private String name;
    private String code;
    private String description;

    @ManyToOne
    @JoinColumn(name="country_id")
    private Country country;

    public State(){}

    public State(Integer id, String name, String code, String description, Country country) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.description = description;
        this.country = country;
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
    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
