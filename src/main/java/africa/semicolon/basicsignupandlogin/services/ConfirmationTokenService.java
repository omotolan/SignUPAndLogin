package africa.semicolon.basicsignupandlogin.services;

import africa.semicolon.basicsignupandlogin.data.models.ConfirmationToken;
import africa.semicolon.basicsignupandlogin.data.models.User;

import java.util.Optional;

public interface ConfirmationTokenService {
    void saveConfirmationTokenRepository(ConfirmationToken token);
//
//    Optional<Object> getToken(String token);
//
//    void setConfirmedAt(String token);
}
