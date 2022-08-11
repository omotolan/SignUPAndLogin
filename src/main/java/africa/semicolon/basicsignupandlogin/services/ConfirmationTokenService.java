package africa.semicolon.basicsignupandlogin.services;

import africa.semicolon.basicsignupandlogin.data.models.ConfirmationToken;

import java.util.Optional;

public interface ConfirmationTokenService {
    void saveConfirmationTokenRepository(ConfirmationToken token);
    Optional<ConfirmationToken> getToken(String token);
    void setConfirmedAt(String token);
}
