package africa.semicolon.basicsignupandlogin.services;

import africa.semicolon.basicsignupandlogin.data.models.User;
import africa.semicolon.basicsignupandlogin.dto.LoginRequest;
import africa.semicolon.basicsignupandlogin.dto.SignUpRequest;
import africa.semicolon.basicsignupandlogin.dto.SignUpResponse;

import java.util.List;

public interface SignUpService {
    SignUpResponse signUp(SignUpRequest signUpRequest);
    void login(LoginRequest loginRequest);

    List<User> getAllUsers();

    String deleteUser(Long id);
}
