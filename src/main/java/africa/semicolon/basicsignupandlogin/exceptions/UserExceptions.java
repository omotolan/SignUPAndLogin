package africa.semicolon.basicsignupandlogin.exceptions;

public class UserExceptions extends Exception {

    private final String message;
    public UserExceptions(String message){
        this.message = message;
    }
    @Override
    public String toString() {
        return "UserExceptions{" +
                "message='" + message + '\'' +
                '}';
    }
}
