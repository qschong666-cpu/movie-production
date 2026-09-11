package cinema_backend.api_movie_system.models;

// Flat response shape expected by the Angular UserService.userBasicDetails() call
public class UserBasicDetailsResponse {

    private String errorCode = "0";
    private String message = "Success";
    private long referenceId = 0;
    private UserBasicDetailsModel userBasicDetails;

    public UserBasicDetailsResponse() {
    }

    public UserBasicDetailsResponse(UserBasicDetailsModel userBasicDetails) {
        this.userBasicDetails = userBasicDetails;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(long referenceId) {
        this.referenceId = referenceId;
    }

    public UserBasicDetailsModel getUserBasicDetails() {
        return userBasicDetails;
    }

    public void setUserBasicDetails(UserBasicDetailsModel userBasicDetails) {
        this.userBasicDetails = userBasicDetails;
    }
}
