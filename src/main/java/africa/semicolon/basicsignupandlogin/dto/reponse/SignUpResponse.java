package africa.semicolon.basicsignupandlogin.dto.reponse;


import africa.semicolon.basicsignupandlogin.dto.UserDto;
import africa.semicolon.basicsignupandlogin.dto.reponse.Response;

public class SignUpResponse extends Response {
    private final UserDto userDto;
    public SignUpResponse(String message, UserDto userDto){
        super(message);

        this.userDto = userDto;
    }

    @Override
    public String toString() {
        return "SignUpResponse{" +
                "userDto=" + userDto +
                '}';
    }
}
