package africa.semicolon.basicsignupandlogin.controller;

import africa.semicolon.basicsignupandlogin.apiresponse.ApiResponse;
import africa.semicolon.basicsignupandlogin.dto.SignUpRequest;
import africa.semicolon.basicsignupandlogin.services.SignUpService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class UserController {
    private final SignUpService signUpService;

    @PostMapping("/user/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest signUpRequest){
        var serviceResponse = signUpService.signUp(signUpRequest);
        ApiResponse apiResponse = new ApiResponse(true, serviceResponse);
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }


    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token) {
        return signUpService.confirmToken(token);
    }
   // @PostMapping("/admin")

}
