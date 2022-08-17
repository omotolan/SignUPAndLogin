package africa.semicolon.basicsignupandlogin.services;

import africa.semicolon.basicsignupandlogin.data.models.ConfirmationToken;
import africa.semicolon.basicsignupandlogin.exceptions.TokenException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ConfirmationTokenImplTest {

    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    @Test
    void getToken() throws TokenException {
        ConfirmationToken confirmationToken = confirmationTokenService.findById(1L);
        assertEquals("fdfs", confirmationToken.getToken());
    }
    @Test
    void getT() throws TokenException {
        ConfirmationToken confirmationToken = confirmationTokenService.getToken("ca352");
//        assertEquals(2L, confirmationToken.getId());
        assertEquals("tol", confirmationToken.getUser().getFirstName());
    }
}