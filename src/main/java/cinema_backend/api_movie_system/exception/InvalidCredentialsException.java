package cinema_backend.api_movie_system.exception;

// Thrown for both "email not found" and "wrong password" so we never reveal
// to a caller which one of the two actually failed
public class InvalidCredentialsException extends RuntimeException {

    public InvalidCredentialsException(String message) {
        super(message);
    }
}
