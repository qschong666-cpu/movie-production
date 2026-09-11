package cinema_backend.api_movie_system.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

// Matches the Angular UserBasicDetailsModel interface used right after login
public class UserBasicDetailsModel {

    private String name;
    private String username;
    private String email;
    private String roleCode;
    private String roleName;
    private Integer orgId = 0; // not used by this domain (branch-based, not org-based); kept for frontend compatibility
    private List<Integer> branchIds;
    @JsonProperty("isActive")
    private boolean isActive;
    private List<PermissionCode> permissionCode;

    public UserBasicDetailsModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public List<Integer> getBranchIds() {
        return branchIds;
    }

    public void setBranchIds(List<Integer> branchIds) {
        this.branchIds = branchIds;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<PermissionCode> getPermissionCode() {
        return permissionCode;
    }

    public void setPermissionCode(List<PermissionCode> permissionCode) {
        this.permissionCode = permissionCode;
    }
}
