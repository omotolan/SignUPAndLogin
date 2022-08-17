package africa.semicolon.basicsignupandlogin.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class LoginRequest {
    private String email;
    private String password;
}
