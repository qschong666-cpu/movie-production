package cinema_backend.api_movie_system.config;

import cinema_backend.api_movie_system.repository.UserRepository;
import cinema_backend.api_movie_system.service.PermissionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // turns on @PreAuthorize on controller/service methods
public class SecurityConfig {

    // BCrypt: one-way salted hash, safe to store; verification re-hashes the input and compares
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(
        JwtUtil jwtUtil,
        UserRepository userRepository,
        PermissionService permissionService
    ) {
        return new JwtAuthenticationFilter(jwtUtil, userRepository, permissionService);
    }

    // Login/refresh are public; everything else requires a valid JWT. Fine-grained module/action
    // checks (e.g. "can this user create a movie?") are enforced with @PreAuthorize on controller
    // methods, using the ROLE_* / PERM_<MODULE>_<ACTION> authorities the JWT filter attaches above.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(Customizer.withDefaults())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/Auth/**").permitAll()
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

