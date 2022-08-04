package africa.semicolon.basicsignupandlogin.services;

import africa.semicolon.basicsignupandlogin.data.models.ConfirmationToken;
import africa.semicolon.basicsignupandlogin.data.repositories.ConfirmationTokenRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class ConfirmationTokenImpl implements ConfirmationTokenService{
private final ConfirmationTokenRepository confirmationTokenRepository;
@Override
public void saveConfirmationTokenRepository(ConfirmationToken token){
    confirmationTokenRepository.save(token);
}



}
