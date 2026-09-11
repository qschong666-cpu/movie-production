package cinema_backend.api_movie_system.service;

public interface EmailService {
    void sendOnboardingEmail(String recipientEmail, String token);
}
