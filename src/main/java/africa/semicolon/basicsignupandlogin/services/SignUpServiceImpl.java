package africa.semicolon.basicsignupandlogin.services;

import africa.semicolon.basicsignupandlogin.data.models.ConfirmationToken;
import africa.semicolon.basicsignupandlogin.data.models.MyUserDetailsService;
import africa.semicolon.basicsignupandlogin.data.models.User;
import africa.semicolon.basicsignupandlogin.data.repositories.UserRepository;
import africa.semicolon.basicsignupandlogin.dto.LoginRequest;
import africa.semicolon.basicsignupandlogin.dto.SignUpRequest;
import africa.semicolon.basicsignupandlogin.dto.SignUpResponse;
import africa.semicolon.basicsignupandlogin.dto.UserDto;
import africa.semicolon.basicsignupandlogin.exceptions.InvalidPasswordException;
import africa.semicolon.basicsignupandlogin.exceptions.UserAlreadyExistException;
import africa.semicolon.basicsignupandlogin.exceptions.UserDoesNotExistException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class SignUpServiceImpl implements SignUpService {
    private final UserRepository repository;
    private final MyUserDetailsService userDetailsService;
    private final ModelMapper mapper;
    private final ConfirmationTokenService confirmationTokenService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public SignUpResponse signUp(SignUpRequest signUpRequest) {
        validateEmail(signUpRequest.getEmail());

        User user = mapper.map(signUpRequest, User.class);
        //log.info("");
        String encodedPassword = bCryptPasswordEncoder.encode(signUpRequest.getPassword());
        user.setPassword(encodedPassword);

        User registeredUser = repository.save(user);
        log.info("new user was created" + signUpRequest.getEmail());

        String generatedToken = GenerateToken.generateToken();
        ConfirmationToken confirmationToken = new ConfirmationToken();

        confirmationToken.setToken(generatedToken);
        confirmationToken.setUser(registeredUser);
        confirmationTokenService.saveConfirmationTokenRepository(confirmationToken);
        return new SignUpResponse("Successfully registered. ", UserDto.pack(registeredUser));
    }

    private void validateEmail(String email) {
        Optional<User> user = repository.findByEmail(email);
        if (user.isPresent()) {
            throw new UserAlreadyExistException("Email already exist");
        }
    }

    @Override
    public void login(LoginRequest loginRequest) {
        Optional<User> user = repository.findByEmail(loginRequest.getEmail());
        if (user.isEmpty()) {
            throw new UserDoesNotExistException("email does not exist");
        }
        if (!Objects.equals(user.get().getPassword(), loginRequest.getPassword())) {
            throw new InvalidPasswordException("Invalid Password");
        }
        log.info(loginRequest.getEmail() + "logged in successfully");

    }

    @Override
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    @Override
    public String deleteUser(Long id) {
        Optional<User> user = repository.findById(id);
        if (user.isEmpty()) {
            throw new UserDoesNotExistException("User with id: " + id + " does not exist");
        }
        repository.deleteById(id);

        return "user deleted";
    }


}
