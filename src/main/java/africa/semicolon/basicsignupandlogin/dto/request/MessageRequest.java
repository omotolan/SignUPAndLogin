package africa.semicolon.basicsignupandlogin.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class MessageRequest {
    private String receiver;
    private String body;
    private String subject;
    private String sender;
}
