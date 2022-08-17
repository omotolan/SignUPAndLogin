package africa.semicolon.basicsignupandlogin.services;

import africa.semicolon.basicsignupandlogin.data.models.ConfirmationToken;
import africa.semicolon.basicsignupandlogin.data.models.User;
import africa.semicolon.basicsignupandlogin.data.models.UserRole;
import africa.semicolon.basicsignupandlogin.data.repositories.UserRepository;
import africa.semicolon.basicsignupandlogin.dto.*;
import africa.semicolon.basicsignupandlogin.exceptions.TokenException;
import africa.semicolon.basicsignupandlogin.exceptions.UserAlreadyExistException;
import africa.semicolon.basicsignupandlogin.exceptions.UserDoesNotExistException;
import africa.semicolon.basicsignupandlogin.services.email.EmailService;
import africa.semicolon.basicsignupandlogin.utils.GenerateToken;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
@AllArgsConstructor
//@Transactional
public class UserAuthServiceImpl implements UserAuthService, UserDetailsService {
    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private final ConfirmationTokenService confirmationTokenService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmailService sendMail;

    @Override
    public SignUpResponse signUp(SignUpRequest signUpRequest) throws UnirestException, UserAlreadyExistException {
        validateEmail(signUpRequest.getEmail());

        User user = mapper.map(signUpRequest, User.class);
        String encodedPassword = bCryptPasswordEncoder.encode(signUpRequest.getPassword());
        user.setPassword(encodedPassword);
        User registeredUser = userRepository.save(user);

        log.info("new user was created " + registeredUser.getEmail());

        String generatedToken = GenerateToken.generateToken();
        ConfirmationToken confirmationToken = new ConfirmationToken();
        confirmationToken.setToken(generatedToken);
        confirmationToken.setUser(registeredUser);
        confirmationToken.setCreatedAt(LocalDateTime.now());
        confirmationToken.setExpiresAt(LocalDateTime.now().plusMinutes(15));
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        log.info("token generate and saved");

        String link = "http://localhost:8080/api/v1/registration/confirm?token=" + generatedToken;
        MessageRequest messageRequest = new MessageRequest();
        messageRequest.setSender("ehizman.tutoredafrica@gmail.com");
        messageRequest.setReceiver(registeredUser.getEmail());
        messageRequest.setBody("Kindly verify your account. " + link);
        messageRequest.setSubject("Confirmation Token");
        sendMail.sendSimpleMessage(messageRequest);

        log.info("email sent to: " + messageRequest.getReceiver());

        return new SignUpResponse("sign up succefully, a verification link was sent to your" +
                " mail. Kinldy verify your mail.",  UserDto.pack(registeredUser));
    }

    private void validateEmail(String email) throws UserAlreadyExistException {
        Optional<User> user = userRepository.findUserByEmail(email);
        if (user.isPresent()) {
            throw new UserAlreadyExistException("Email already exist");
        }
    }

    @Override
    public String confirmToken(String token) throws TokenException {

        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token);

        log.info("this is the token : "  + confirmationToken.getToken());


        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }
        confirmationToken.setConfirmedAt(LocalDateTime.now());
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        User user = confirmationToken.getUser();
        user.setIsVerified(Boolean.TRUE);

        userRepository.save(user);


        return "confirmed";
    }

    @Override
    public User getUserByEmail(String email) throws UserDoesNotExistException {
        return findUserByEmail(email);
    }

    private User findUserByEmail(String email) throws UserDoesNotExistException {
        Optional<User> user = userRepository.findUserByEmail(email);
        if (user.isEmpty()) {
            throw new UserDoesNotExistException("User does not exist");
        }
        return user.get();

    }
    public ConfirmationToken findById(Long id) throws TokenException {
       return confirmationTokenService.findById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = null;
        try {
            user = userRepository.findUserByEmail(email).orElseThrow(() -> new UserDoesNotExistException("user not found"));
        } catch (UserDoesNotExistException e) {

            throw new RuntimeException(e);
        }
        org.springframework.security.core.userdetails.User returnedUser = new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthorities(user.getUserRole()));
        log.info("Returned user --> {}", returnedUser);
        return returnedUser;
    }

    private Collection<? extends GrantedAuthority> getAuthorities(UserRole role) {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.name()));
        return authorities;
    }

      /* private Collection<? extends GrantedAuthority> getAuthorities(Set<Role> roles) {
        Collection<? extends SimpleGrantedAuthority> authorities = roles.stream().map(
                role -> new SimpleGrantedAuthority(role.getRoleType().name())
        ).collect(Collectors.toSet());
        return authorities;
    } **/
}
