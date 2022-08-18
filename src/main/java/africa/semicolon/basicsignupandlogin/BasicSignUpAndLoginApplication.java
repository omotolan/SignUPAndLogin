package africa.semicolon.basicsignupandlogin;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
public class BasicSignUpAndLoginApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasicSignUpAndLoginApplication.class, args);
	}

}
