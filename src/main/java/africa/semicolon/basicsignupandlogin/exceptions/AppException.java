package africa.semicolon.basicsignupandlogin.exceptions;

public class AppException extends Exception {

    private final String message;
    public AppException(String message){
        this.message = message;
    }
    @Override
    public String toString() {
        return "UserExceptions{" +
                "message='" + message + '\'' +
                '}';
    }
}
