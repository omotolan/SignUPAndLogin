package africa.semicolon.basicsignupandlogin.controller;

import africa.semicolon.basicsignupandlogin.controller.apiresponse.ApiResponse;
import africa.semicolon.basicsignupandlogin.controller.apiresponse.AuthToken;
import africa.semicolon.basicsignupandlogin.data.models.User;
import africa.semicolon.basicsignupandlogin.dto.LoginRequest;
import africa.semicolon.basicsignupandlogin.dto.SignUpRequest;
import africa.semicolon.basicsignupandlogin.security.jwt.TokenProvider;
import africa.semicolon.basicsignupandlogin.services.UserAuthService;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
@Slf4j
public class UserAuthController {
    private final UserAuthService userAuthService;
    private final TokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/user/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest signUpRequest) throws UnirestException {
        var serviceResponse = userAuthService.signUp(signUpRequest);
        ApiResponse apiResponse = new ApiResponse(true, serviceResponse);
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token) {
        return userAuthService.confirmToken(token);
    }

    @PostMapping("/user/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
       Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = tokenProvider.generateJWTToken(authentication);
        User user = userAuthService.getUserByEmail(loginRequest.getEmail());
        return new ResponseEntity<>(new AuthToken(token, user.getId()), HttpStatus.OK);
    }


    @GetMapping("/hi")
    public String hello() {
        return "hello";
    }
}
