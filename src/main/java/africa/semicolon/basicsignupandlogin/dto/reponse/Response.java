package africa.semicolon.basicsignupandlogin.dto.reponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class Response {
    private String message;
    @Override
    public String toString(){
        return message;
    }
}
