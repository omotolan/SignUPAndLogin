package africa.semicolon.basicsignupandlogin.services;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GenerateToken {

    public static String generateToken(){
        return UUID.randomUUID().toString();
    }

}
