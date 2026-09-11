package cinema_backend.api_movie_system.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// One row = one action allowed on one module, e.g. MOVIE + CREATE.
// GLOBAL_ADMIN never needs rows here - it implicitly gets every permission (see PermissionServiceimpl).
@Entity
@Table(name = "ref_permission")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Unique machine code, e.g. "MOVIE_CREATE"
    @Column(nullable = false, unique = true)
    private String code;

    @Column(name = "module_code", nullable = false)
    private String moduleCode;

    @Column(name = "permission_type_code", nullable = false)
    private String permissionTypeCode;

    private String name;

    private String description;

    public Permission() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getPermissionTypeCode() {
        return permissionTypeCode;
    }

    public void setPermissionTypeCode(String permissionTypeCode) {
        this.permissionTypeCode = permissionTypeCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
