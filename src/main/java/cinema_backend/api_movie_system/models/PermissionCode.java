package cinema_backend.api_movie_system.models;

// Mirrors the frontend's PermissionCode interface: { moduleCode, permissionTypeCode }
public class PermissionCode {

    private String moduleCode;
    private String permissionTypeCode;

    public PermissionCode() {
    }

    public PermissionCode(String moduleCode, String permissionTypeCode) {
        this.moduleCode = moduleCode;
        this.permissionTypeCode = permissionTypeCode;
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
}
