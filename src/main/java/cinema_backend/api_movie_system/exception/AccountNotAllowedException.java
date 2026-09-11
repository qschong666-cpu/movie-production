package cinema_backend.api_movie_system.exception;

// Thrown when an account exists but is disabled (status = false) or its role
// is not permitted to sign in to this particular application
public class AccountNotAllowedException extends RuntimeException {

    public AccountNotAllowedException(String message) {
        super(message);
    }
}
