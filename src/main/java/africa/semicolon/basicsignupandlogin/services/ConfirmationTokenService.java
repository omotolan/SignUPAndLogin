package africa.semicolon.basicsignupandlogin.services;

import africa.semicolon.basicsignupandlogin.data.models.ConfirmationToken;
import africa.semicolon.basicsignupandlogin.exceptions.TokenException;

import java.util.Optional;

public interface ConfirmationTokenService {
    void saveConfirmationToken(ConfirmationToken token);

    ConfirmationToken getToken(String token) throws TokenException;

    ConfirmationToken findById(Long id) throws TokenException;

    void deleteExpiredToken();
}
