package africa.semicolon.basicsignupandlogin.services;

import africa.semicolon.basicsignupandlogin.data.models.User;
import africa.semicolon.basicsignupandlogin.dto.LoginRequest;
import africa.semicolon.basicsignupandlogin.dto.SignUpRequest;
import africa.semicolon.basicsignupandlogin.dto.UserDto;
import com.mashape.unirest.http.exceptions.UnirestException;


public interface UserAuthService {
    UserDto signUp(SignUpRequest signUpRequest) throws UnirestException;
    String confirmToken(String token);
    User getUserByEmail(String email);
}
