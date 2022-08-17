package africa.semicolon.basicsignupandlogin.dto;


public class SignUpResponse extends Response {
    private final UserDto userDto;
    public SignUpResponse(String message, UserDto userDto){
        super(message);

        this.userDto = userDto;
    }


}
