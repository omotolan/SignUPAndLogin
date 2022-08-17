package africa.semicolon.basicsignupandlogin.controller.apiresponse;

import lombok.*;



@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {
    private boolean isSuccessful;

    private Object data;

}
