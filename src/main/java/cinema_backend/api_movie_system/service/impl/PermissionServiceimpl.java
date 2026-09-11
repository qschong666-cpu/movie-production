package cinema_backend.api_movie_system.service.impl;

import cinema_backend.api_movie_system.models.Permission;
import cinema_backend.api_movie_system.models.PermissionCode;
import cinema_backend.api_movie_system.models.User;
import cinema_backend.api_movie_system.models.UserPermission;
import cinema_backend.api_movie_system.repository.PermissionRepository;
import cinema_backend.api_movie_system.repository.UserPermissionRepository;
import cinema_backend.api_movie_system.service.PermissionService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PermissionServiceimpl implements PermissionService {

    private static final String GLOBAL_ADMIN = "GLOBAL_ADMIN";

    private final PermissionRepository permissionRepository;
    private final UserPermissionRepository userPermissionRepository;

    public PermissionServiceimpl(
        PermissionRepository permissionRepository,
        UserPermissionRepository userPermissionRepository
    ) {
        this.permissionRepository = permissionRepository;
        this.userPermissionRepository = userPermissionRepository;
    }

    @Override
    public List<PermissionCode> getEffectivePermissions(User user) {
        // GLOBAL_ADMIN implicitly has every permission that exists - no rows to maintain
        if (isGlobalAdmin(user)) {
            return permissionRepository.findAll().stream()
                .map(p -> new PermissionCode(p.getModuleCode(), p.getPermissionTypeCode()))
                .toList();
        }

        // Everyone else (SUB_ADMIN, BRANCH_MANAGER, STAFF) only gets what was explicitly granted
        return userPermissionRepository.findByUser_Id(user.getId()).stream()
            .map(UserPermission::getPermission)
            .map(p -> new PermissionCode(p.getModuleCode(), p.getPermissionTypeCode()))
            .toList();
    }

    @Override
    public List<String> getAuthorities(User user) {
        String roleCode = user.getRole() != null ? user.getRole().getCode() : null;

        List<String> authorities = new java.util.ArrayList<>();
        if (roleCode != null) {
            authorities.add("ROLE_" + roleCode);
        }

        for (PermissionCode permission : getEffectivePermissions(user)) {
            authorities.add("PERM_" + permission.getModuleCode() + "_" + permission.getPermissionTypeCode());
        }

        return authorities;
    }

    private boolean isGlobalAdmin(User user) {
        return user.getRole() != null && GLOBAL_ADMIN.equals(user.getRole().getCode());
    }
}
