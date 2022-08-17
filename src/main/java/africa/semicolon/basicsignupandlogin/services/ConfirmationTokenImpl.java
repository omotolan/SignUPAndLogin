package africa.semicolon.basicsignupandlogin.services;

import africa.semicolon.basicsignupandlogin.data.models.ConfirmationToken;
import africa.semicolon.basicsignupandlogin.data.repositories.ConfirmationTokenRepository;
import africa.semicolon.basicsignupandlogin.exceptions.TokenException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class ConfirmationTokenImpl implements ConfirmationTokenService {
    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Override
    public void saveConfirmationToken(ConfirmationToken token) {
        confirmationTokenRepository.save(token);
    }

    @Override
    public ConfirmationToken getToken(String token) throws TokenException {
        return findToken(token);
    }

    @Override
    public ConfirmationToken findById(Long id) throws TokenException {
        Optional<ConfirmationToken> confirmationToken = confirmationTokenRepository.findById(id);
        if (confirmationToken.isEmpty()) {
            throw new TokenException("token not found");
        }
        return confirmationToken.get();
    }

    private ConfirmationToken findToken(String token) throws TokenException {
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token);
        if (confirmationToken == null) {
            throw new TokenException("token not found");
        }
        return confirmationToken;

    }

    public void deleteExpiredToken() {
        confirmationTokenRepository.findAll();
    }


}
