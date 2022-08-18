package africa.semicolon.basicsignupandlogin.exceptions;

public class UserDoesNotExistException extends AppException {
    public UserDoesNotExistException(String message) {
        super(message);
    }
}
