package cinema_backend.api_movie_system.service;

import cinema_backend.api_movie_system.models.PermissionCode;
import cinema_backend.api_movie_system.models.User;
import java.util.List;

public interface PermissionService {
    // Full permission list a user effectively has (GLOBAL_ADMIN = every permission in ref_permission)
    List<PermissionCode> getEffectivePermissions(User user);

    // Spring Security authority strings for the JWT filter, e.g. "ROLE_GLOBAL_ADMIN", "PERM_MOVIE_CREATE"
    List<String> getAuthorities(User user);
}
