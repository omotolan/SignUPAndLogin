package africa.semicolon.basicsignupandlogin.services.email;

import africa.semicolon.basicsignupandlogin.dto.MessageRequest;
import com.mashape.unirest.http.exceptions.UnirestException;

public interface EmailService {
    void sendSimpleMessage(MessageRequest messageRequest) throws UnirestException;

}
