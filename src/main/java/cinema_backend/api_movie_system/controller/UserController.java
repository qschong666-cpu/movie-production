package cinema_backend.api_movie_system.controller;

import cinema_backend.api_movie_system.models.User;
import cinema_backend.api_movie_system.models.UserBasicDetailsModel;
import cinema_backend.api_movie_system.models.UserBasicDetailsResponse;
import cinema_backend.api_movie_system.service.PermissionService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/User")
public class UserController {

    private final PermissionService permissionService;

    public UserController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    // Called once right after login (and on app refresh) to hydrate the current user + their
    // permissions; the JwtAuthenticationFilter has already resolved "user" from the bearer token
    @GetMapping("/UserBasicDetails")
    public UserBasicDetailsResponse getUserBasicDetails(@AuthenticationPrincipal User user) {
        UserBasicDetailsModel details = new UserBasicDetailsModel();
        details.setName(user.getName());
        details.setUsername(user.getEmail());
        details.setEmail(user.getEmail());
        details.setActive(Boolean.TRUE.equals(user.getStatus()));

        if (user.getRole() != null) {
            details.setRoleCode(user.getRole().getCode());
            details.setRoleName(user.getRole().getName());
        }

        details.setPermissionCode(permissionService.getEffectivePermissions(user));

        return new UserBasicDetailsResponse(details);
    }
}
