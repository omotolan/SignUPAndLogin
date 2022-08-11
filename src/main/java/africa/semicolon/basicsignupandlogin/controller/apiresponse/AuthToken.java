package africa.semicolon.basicsignupandlogin.controller.apiresponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class AuthToken {
    private String token;
    private Long id;

}
