package africa.semicolon.basicsignupandlogin.dto.reponse;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Response {
    private String message;
    @Override
    public String toString(){
        return message;
    }
}
