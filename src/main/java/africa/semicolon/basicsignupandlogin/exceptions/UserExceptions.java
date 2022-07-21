package africa.semicolon.basicsignupandlogin.exceptions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserExceptions extends RuntimeException {
    private String message;
}
