package africa.semicolon.basicsignupandlogin.utils;

import africa.semicolon.basicsignupandlogin.services.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@Configuration
@AllArgsConstructor
@EnableScheduling
public class DeleteExpiredTokenScheduler {

    private ConfirmationTokenService confirmationToken;

    @Async
    @Scheduled(cron = "0 0 */23 * * *") //sec,min,hour,day, month day_of_the_week
    public void scheduledDelete() {
        confirmationToken.deleteExpiredToken();
        log.info("expired token(s) cleared");

    }

}
