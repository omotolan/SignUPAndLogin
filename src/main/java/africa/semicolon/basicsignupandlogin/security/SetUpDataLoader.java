package africa.semicolon.basicsignupandlogin.security;

import africa.semicolon.basicsignupandlogin.data.models.User;
import africa.semicolon.basicsignupandlogin.data.models.UserRole;
import africa.semicolon.basicsignupandlogin.data.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;


@Component
@Slf4j
@AllArgsConstructor
public class SetUpDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        String password = System.getenv("ADMIN_PASSWORD");
        if (userRepository.findUserByEmailIgnoreCase("admin@yahoo.com").isEmpty()) {
            User user = new User();
            user.setFirstName("admin");
            user.setLastName("user");
            user.setEmail("admin@yahoo.com");
            user.setIsVerified(Boolean.TRUE);
            user.setPhoneNumber("08182769505");
            user.setPassword(bCryptPasswordEncoder.encode(password));
            user.setUserRole(UserRole.ADMIN);
            userRepository.save(user);
        }
    }
}
