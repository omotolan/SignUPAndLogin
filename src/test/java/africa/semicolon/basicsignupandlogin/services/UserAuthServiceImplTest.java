package africa.semicolon.basicsignupandlogin.services;

import africa.semicolon.basicsignupandlogin.data.models.UserRole;
import africa.semicolon.basicsignupandlogin.dto.request.SignUpRequest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserAuthServiceImplTest {
    @Autowired
    private UserAuthService userAuthService;
//    @Autowired
//    private UserRepository userRepository;
//
//    @BeforeEach
//    void setUp() {
//        userRepository.deleteAll();
//    }

    @Test
   public void signUp() throws UnirestException {
        SignUpRequest signUpRequest = SignUpRequest.builder()
                .email("akinsolakolawolee@gmail.com")
                .userRole(UserRole.ADMIN)
                .password("1234")
                .build();
//       String signUpResponse = userAuthService.signUp(signUpRequest);
//       assertEquals(" ", signUpResponse);
       // assertEquals("Successfully registered.", signUpResponse.toString());
    }
}