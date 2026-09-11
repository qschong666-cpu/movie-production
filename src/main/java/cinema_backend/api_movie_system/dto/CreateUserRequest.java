package cinema_backend.api_movie_system.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class CreateUserRequest {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Company email is required")
    @Email(message = "Invalid company email format")
    private String companyEmail;

    @NotBlank(message = "Personal email is required")
    @Email(message = "Invalid personal email format")
    private String personalEmail; // Recipient for the activation email link

    private String phone; // Optional field

    @NotBlank(message = "Role code is required")
    private String roleCode; // Expected values: "SUB_ADMIN" or "BRANCH_MANAGER"

    public String getCompanyEmail() {
        return companyEmail;
    }

    public String getPersonalEmail() {
        return personalEmail;
    }

    public String getPhone() {
        return phone;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    public void setPersonalEmail(String personalEmail) {
        this.personalEmail = personalEmail;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }
    
}
