package cinema_backend.api_movie_system.service;

import cinema_backend.api_movie_system.models.LoginRequest;
import cinema_backend.api_movie_system.models.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest loginRequest);
}
