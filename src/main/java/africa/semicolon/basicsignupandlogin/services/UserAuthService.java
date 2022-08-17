package africa.semicolon.basicsignupandlogin.services;

import africa.semicolon.basicsignupandlogin.data.models.ConfirmationToken;
import africa.semicolon.basicsignupandlogin.data.models.User;
import africa.semicolon.basicsignupandlogin.dto.SignUpRequest;
import africa.semicolon.basicsignupandlogin.dto.SignUpResponse;
import africa.semicolon.basicsignupandlogin.exceptions.TokenException;
import africa.semicolon.basicsignupandlogin.exceptions.UserAlreadyExistException;
import africa.semicolon.basicsignupandlogin.exceptions.UserDoesNotExistException;
import com.mashape.unirest.http.exceptions.UnirestException;


public interface UserAuthService {
    SignUpResponse signUp(SignUpRequest signUpRequest) throws UnirestException, UserAlreadyExistException;
    String confirmToken(String token) throws TokenException;
    User getUserByEmail(String email) throws UserDoesNotExistException;
    ConfirmationToken findById(Long id) throws TokenException;
}
