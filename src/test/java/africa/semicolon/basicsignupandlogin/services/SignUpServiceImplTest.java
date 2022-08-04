package africa.semicolon.basicsignupandlogin.services;

import africa.semicolon.basicsignupandlogin.data.models.UserRole;
import africa.semicolon.basicsignupandlogin.dto.SignUpRequest;
import africa.semicolon.basicsignupandlogin.dto.SignUpResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class SignUpServiceImplTest {
    @Autowired
    private SignUpService signUpService;

    @Test
   public void signUp() {
        SignUpRequest signUpRequest = SignUpRequest.builder()
                .email("latolani@yahoo.com")
                .userRole(UserRole.ADMIN)
                .password("1234")
                .build();
       SignUpResponse signUpResponse = signUpService.signUp(signUpRequest);
        assertEquals("Successfully registered.", signUpResponse.toString());
    }
}