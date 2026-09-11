package cinema_backend.api_movie_system.controller;

import cinema_backend.api_movie_system.models.LoginRequest;
import cinema_backend.api_movie_system.models.LoginResponse;
import cinema_backend.api_movie_system.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/Auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Returned as a flat JSON object (not wrapped by ResponseHandler) so it matches the
    // LoginBaseResponse shape the Angular AuthService already expects
    @PostMapping("/Login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }
}
