package cinema_backend.api_movie_system.config;

import cinema_backend.api_movie_system.models.User;
import cinema_backend.api_movie_system.repository.UserRepository;
import cinema_backend.api_movie_system.service.PermissionService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

// Runs on every request: reads "Authorization: Bearer <token>", validates it, and (re)computes
// the caller's role/permission authorities fresh from the database so permission changes made
// by an admin take effect immediately without the affected user needing to log in again.
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PermissionService permissionService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserRepository userRepository, PermissionService permissionService) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.permissionService = permissionService;
    }

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {
        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring("Bearer ".length());

            try {
                Claims claims = jwtUtil.parseClaims(token);
                Integer userId = Integer.valueOf(claims.getSubject());

                userRepository.findById(userId).ifPresent(this::authenticate);
            } catch (JwtException | IllegalArgumentException ex) {
                // Invalid/expired token: leave the request unauthenticated; protected endpoints
                // will be rejected with 401/403 further down the filter chain
                SecurityContextHolder.clearContext();
            }
        }

        filterChain.doFilter(request, response);
    }

    private void authenticate(User user) {
        List<GrantedAuthority> authorities = permissionService.getAuthorities(user).stream()
            .map(SimpleGrantedAuthority::new)
            .map(GrantedAuthority.class::cast)
            .toList();

        var authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
