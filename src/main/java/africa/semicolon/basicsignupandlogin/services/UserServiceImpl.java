package africa.semicolon.basicsignupandlogin.services;

import africa.semicolon.basicsignupandlogin.data.models.User;
import africa.semicolon.basicsignupandlogin.data.repositories.UserRepository;
import africa.semicolon.basicsignupandlogin.dto.LoginRequest;
import africa.semicolon.basicsignupandlogin.dto.SignUpRequest;
import africa.semicolon.basicsignupandlogin.dto.SignUpResponse;
import africa.semicolon.basicsignupandlogin.exceptions.InvalidPasswordException;
import africa.semicolon.basicsignupandlogin.exceptions.UserAlreadyExistException;
import africa.semicolon.basicsignupandlogin.exceptions.UserDoesNotExistException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserServices {
    @Autowired
    private  UserRepository repository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public SignUpResponse signUp(SignUpRequest signUpRequest) {
        User user = repository.findByEmail(signUpRequest.getEmail());
        if (user != null) {
            throw new UserAlreadyExistException("Email already exist");

        }
        String encodedPassword = bCryptPasswordEncoder.encode(signUpRequest.getPassword());

        user = mapper.map(signUpRequest, User.class);
        user.setPassword(encodedPassword);

        repository.save(user);
        log.info("new user was created" + signUpRequest.getEmail());

        return new SignUpResponse("Successfully registered");

    }

    @Override
    public void login(LoginRequest loginRequest) {
        User user = repository.findByEmail(loginRequest.getEmail());
        if (user == null) {
            throw new UserDoesNotExistException("email does not exist");
        }
        if (!Objects.equals(user.getPassword(), loginRequest.getPassword())) {
            throw new InvalidPasswordException("Invalid Password");
        }
        log.info( loginRequest.getEmail() + "logged in successfully");

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
