package africa.semicolon.basicsignupandlogin;

import africa.semicolon.basicsignupandlogin.data.repositories.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class BasicSignUpAndLoginApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasicSignUpAndLoginApplication.class, args);
	}

}
