package cinema_backend.api_movie_system.models;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "tbl_branch")
public class Branch {
    @Id 
    private Integer id;
    private String name;
    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;
    @ManyToOne
    @JoinColumn(name="address_city")
    private City city;
    private Boolean status = true;
    @Column(name = "address_line_1")
    private String address_line_1;
    @Column(name = "address_line_2")
    private String address_line_2;
    @Column(name = "address_line_3")
    private String address_line_3;
    private Boolean isDeleted = false;
    @Column(name = "created_by")
    private Integer createdBy;

    public Branch() {
    }

    public Branch(Integer id, String name, LocalDateTime createdDate, City city, Boolean status, String address_line_1, String address_line_2, String address_line_3,Boolean isDeleted) {
        this.id = id;
        this.name = name;
        this.createdDate = createdDate;
        this.city = city;
        this.status = status;
        this.address_line_1 = address_line_1;
        this.address_line_2 = address_line_2;
        this.address_line_3 = address_line_3;
        this.isDeleted = isDeleted;
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

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getAddress_line_1() {
        return address_line_1;
    }

    public void setAddress_line_1(String address_line_1) {
        this.address_line_1 = address_line_1;
    }

    public String getAddress_line_2() {
        return address_line_2;
    }

    public void setAddress_line_2(String address_line_2) {
        this.address_line_2 = address_line_2;
    }

    public String getAddress_line_3() {
        return address_line_3;
    }

    public void setAddress_line_3(String address_line_3) {
        this.address_line_3 = address_line_3;
    }

    public Boolean getIsDeleted(){
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted){
        this.isDeleted = isDeleted;
    }   

    public Integer getCreatedBy(){
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy){
        this.createdBy = createdBy;
    }

    @PrePersist
    public void prePersist(){
        if(this.createdDate == null){
            this.createdDate = LocalDateTime.now();
        }
        if(this.status == null){
            this.status = true;
        }
        if(this.isDeleted == null){
            this.isDeleted=false;
        }
    }

    public void prepareForCreate(){
        prePersist();
    }

    @Override
    public boolean equals(Object o){
        if (this == o){
            return true;
        }
        if (!(o instanceof Branch branch)){
            return false;
        }
        return Objects.equals(id, branch.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
