package africa.semicolon.basicsignupandlogin.dto.request;

import africa.semicolon.basicsignupandlogin.data.models.UserRole;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Validated
public class SignUpRequest {

    @NotBlank(message = "first name can not be empty")
    private String firstName;
    @NotBlank(message = "last name can not be empty")
    private String lastName;
    @Email(message = "email must follow the standard pattern e.g example@mail.com") // can take regex
    private String email;
    @NotBlank(message = "password is required")
    @Size(min = 5, message = "the given password is too short, password must be at least 5 letters long")
    private String password;
    @Size(min = 11, max = 11)
    private String phoneNumber;
    private int age;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
}
