package africa.semicolon.basicsignupandlogin.services;

import africa.semicolon.basicsignupandlogin.data.models.ConfirmationToken;
import africa.semicolon.basicsignupandlogin.data.models.MyUserDetailsService;
import africa.semicolon.basicsignupandlogin.data.models.User;
import africa.semicolon.basicsignupandlogin.data.repositories.UserRepository;
import africa.semicolon.basicsignupandlogin.dto.LoginRequest;
import africa.semicolon.basicsignupandlogin.dto.SignUpRequest;
import africa.semicolon.basicsignupandlogin.dto.SignUpResponse;
import africa.semicolon.basicsignupandlogin.dto.UserDto;
import africa.semicolon.basicsignupandlogin.email.EmailSender;
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

    private EmailSender emailSender;

    @Override
    public String signUp(SignUpRequest signUpRequest) {
        validateEmail(signUpRequest.getEmail());

        User user = mapper.map(signUpRequest, User.class);
        //log.info("");
        String encodedPassword = bCryptPasswordEncoder.encode(signUpRequest.getPassword());
        user.setPassword(encodedPassword);

        User registeredUser = repository.save(user);
        log.info("new user was created " + signUpRequest.getEmail());

        String generatedToken = GenerateToken.generateToken();
        ConfirmationToken confirmationToken = new ConfirmationToken();

        confirmationToken.setToken(generatedToken);
        confirmationToken.setUser(registeredUser);
        confirmationTokenService.saveConfirmationTokenRepository(confirmationToken);
       // return new SignUpResponse("Successfully registered. ", UserDto.pack(registeredUser));

        String link = "http://localhost:8080/api/v1/registration/confirm?token=" + generatedToken;
        emailSender.send(
                registeredUser.getEmail(),
                buildEmail(registeredUser.getFirstName(), link));

        return generatedToken;
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
    @Transactional
    @Override
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        enableAppUser(
                confirmationToken.getUser().getEmail());
        return "confirmed";
    }
    public void enableAppUser(String email) {
        Optional<User> user = repository.findByEmail(email);
        user.ifPresent(value -> value.setEnabled(true));
       // repository.enabled(email);
    }


    private String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }

}
