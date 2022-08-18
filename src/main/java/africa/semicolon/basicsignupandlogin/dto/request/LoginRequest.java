package africa.semicolon.basicsignupandlogin.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class LoginRequest {
    private String email;
    private String password;
}
