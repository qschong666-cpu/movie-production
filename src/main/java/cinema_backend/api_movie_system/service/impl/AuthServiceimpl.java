package cinema_backend.api_movie_system.service.impl;

import cinema_backend.api_movie_system.config.JwtProperties;
import cinema_backend.api_movie_system.config.JwtUtil;
import cinema_backend.api_movie_system.exception.AccountNotAllowedException;
import cinema_backend.api_movie_system.exception.InvalidCredentialsException;
import cinema_backend.api_movie_system.models.LoginRequest;
import cinema_backend.api_movie_system.models.LoginResponse;
import cinema_backend.api_movie_system.models.Role;
import cinema_backend.api_movie_system.models.User;
import cinema_backend.api_movie_system.repository.UserRepository;
import cinema_backend.api_movie_system.service.AuthService;
import java.util.Map;
import java.util.Set;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceimpl implements AuthService {

    // Roles allowed to sign in through the admin portal; AUDIENCE users belong to the customer app
    private static final Set<String> ADMIN_APP_ROLES = Set.of(
        "GLOBAL_ADMIN", "SUB_ADMIN", "BRANCH_MANAGER", "STAFF"
    );

    // Where each role should land after a successful login
    private static final Map<String, String> ROLE_REDIRECTS = Map.of(
        "GLOBAL_ADMIN", "/dashboard",
        "SUB_ADMIN", "/dashboard",
        "BRANCH_MANAGER", "/dashboard",
        "STAFF", "/dashboard",
        "AUDIENCE", "/home"
    );

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final JwtProperties jwtProperties;

    public AuthServiceimpl(
        UserRepository userRepository,
        PasswordEncoder passwordEncoder,
        JwtUtil jwtUtil,
        JwtProperties jwtProperties
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.jwtProperties = jwtProperties;
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmailIgnoreCase(loginRequest.getEmail())
            // Generic message on purpose: don't tell the caller whether the email exists
            .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));

        // BCrypt.matches() re-hashes the raw password with the stored salt and compares digests
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        if (Boolean.FALSE.equals(user.getStatus())) {
            throw new AccountNotAllowedException("This account has been deactivated. Please contact an administrator.");
        }

        Role role = user.getRole();
        String roleCode = role != null ? role.getCode() : null;

        if (roleCode == null || !ADMIN_APP_ROLES.contains(roleCode)) {
            throw new AccountNotAllowedException("This account cannot access the admin portal.");
        }

        String accessToken = jwtUtil.generateAccessToken(user.getId(), user.getEmail(), roleCode);
        String refreshToken = jwtUtil.generateRefreshToken(user.getId());

        LoginResponse response = new LoginResponse();
        response.setAccessToken(accessToken);
        response.setRefreshToken(refreshToken);
        response.setExpiresIn(jwtProperties.getExpirationMs() / 1000); // seconds
        response.setUserId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setRoleCode(roleCode);
        response.setRoleName(role.getName());
        response.setRedirectUrl(ROLE_REDIRECTS.getOrDefault(roleCode, "/dashboard"));

        return response;
    }
}
