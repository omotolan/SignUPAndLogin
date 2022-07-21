package africa.semicolon.basicsignupandlogin.controller;

import africa.semicolon.basicsignupandlogin.apiresponse.ApiResponse;
import africa.semicolon.basicsignupandlogin.dto.SignUpRequest;
import africa.semicolon.basicsignupandlogin.services.UserServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {
    private UserServices userServices;

    @PostMapping("/user/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest signUpRequest){
        var serviceResponse = userServices.signUp(signUpRequest);
        ApiResponse apiResponse = new ApiResponse(true, serviceResponse);
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }
   // @PostMapping("/admin")

}
