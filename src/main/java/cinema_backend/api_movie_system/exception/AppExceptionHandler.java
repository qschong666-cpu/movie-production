package cinema_backend.api_movie_system.exception;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@ControllerAdvice
public class AppExceptionHandler {


//region Movie
@ExceptionHandler(value = {MovieNotFoundException.class
})
public ResponseEntity<Object> handleMovieNotFoundException(MovieNotFoundException movieNotFoundException){

    AppException movieException = new AppException(
        movieNotFoundException.getMessage(), 
        movieNotFoundException.getCause(), 
        HttpStatus.NOT_FOUND

    );

    return new ResponseEntity<>(movieException, HttpStatus.NOT_FOUND);
}
//endregion

//region Branch
@ExceptionHandler(value = {BranchNotFoundException.class})
public ResponseEntity<Object> handleBranchNotFoundException(BranchNotFoundException branchNotFoundException){

    AppException branchException = new AppException(
        branchNotFoundException.getMessage(), 
        branchNotFoundException.getCause(), 
        HttpStatus.NOT_FOUND

    );

    return new ResponseEntity<>(branchException, HttpStatus.NOT_FOUND);
}   

//#endregion


//region File
@ExceptionHandler(value = {MaxUploadSizeExceededException.class})
public ResponseEntity<Object> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex) {
    AppException uploadException = new AppException(
        "Uploaded file is too large. Maximum allowed size is 10MB.",
        ex,
        HttpStatus.CONTENT_TOO_LARGE
    );

    return new ResponseEntity<>(uploadException, HttpStatus.CONTENT_TOO_LARGE);
}
   //#endregion

//region Auth
@ExceptionHandler(value = {InvalidCredentialsException.class})
public ResponseEntity<Object> handleInvalidCredentialsException(InvalidCredentialsException ex) {
    AppException authException = new AppException(
        ex.getMessage(),
        ex.getCause(),
        HttpStatus.UNAUTHORIZED
    );

    return new ResponseEntity<>(authException, HttpStatus.UNAUTHORIZED);
}

@ExceptionHandler(value = {AccountNotAllowedException.class})
public ResponseEntity<Object> handleAccountNotAllowedException(AccountNotAllowedException ex) {
    AppException authException = new AppException(
        ex.getMessage(),
        ex.getCause(),
        HttpStatus.FORBIDDEN
    );

    return new ResponseEntity<>(authException, HttpStatus.FORBIDDEN);
}
//endregion
}