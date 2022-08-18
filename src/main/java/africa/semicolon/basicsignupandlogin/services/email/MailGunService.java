package africa.semicolon.basicsignupandlogin.services.email;

import africa.semicolon.basicsignupandlogin.dto.request.MessageRequest;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class MailGunService implements EmailService {

    private final String DOMAIN = System.getenv("DOMAIN");
    private final String PRIVATE_KEY = System.getenv("PRIVATE_KEY");
    @Async
    @Override
    public void sendSimpleMessage(MessageRequest messageRequest) throws UnirestException {

        log.info(" checking env " + PRIVATE_KEY);
        log.info(" checking env " + DOMAIN);
        HttpResponse<String> request = Unirest.post("https://api.mailgun.net/v3/" + DOMAIN + "/messages")
                .basicAuth("api", PRIVATE_KEY)
                .queryString("from", messageRequest.getSender())
                .queryString("to", messageRequest.getReceiver())
                .queryString("subject", messageRequest.getSubject())
                .queryString("text", messageRequest.getBody())
//            .asJson();
                .asString();
        request.getBody();
    }

//    public CompletableFuture<MailResponse> sendSimpleMessage(MessageRequest messageRequest) throws UnirestException {
//        String DOMAIN = dotenv.get("DOMAIN");
//        String PRIVATE_KEY = dotenv.get("PRIVATE_KEY");
//        log.info(" checking env " + PRIVATE_KEY);
//        log.info(" checking env " + DOMAIN);
//        HttpResponse<String> request = Unirest.post("https://api.mailgun.net/v3/" + DOMAIN + "/messages")
//                .basicAuth("api", PRIVATE_KEY)
//                .queryString("from", messageRequest.getSender())
//                .queryString("to", messageRequest.getReceiver())
//                .queryString("subject", messageRequest.getSubject())
//                .queryString("text", messageRequest.getBody())
////            .asJson();
//                .asString();
//       // request.getBody();
//        MailResponse mailResponse = request.getStatus() == 200 ? new MailResponse(true) : new MailResponse(false);
//        return CompletableFuture.completedFuture(mailResponse);
//    }
}
