package africa.semicolon.basicsignupandlogin.security;

import africa.semicolon.basicsignupandlogin.security.jwt.ExceptionHandlerFilter;
import africa.semicolon.basicsignupandlogin.security.jwt.JwtAuthenticationFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@AllArgsConstructor
@EnableGlobalMethodSecurity(
        prePostEnabled = true
)
public class SecurityConfiguration {

    private final UnAuthorizedEntryPoint unAuthorizedEntryPoint;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeHttpRequests(authorize -> {
                    try {
                        authorize.antMatchers("/**/user/signup", "/**/user/login","/**/user/confirm").permitAll()
                                .anyRequest().authenticated()
                                .and()
                                .exceptionHandling()
                                .authenticationEntryPoint(unAuthorizedEntryPoint)
                                .and().sessionManagement()
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
        http.addFilterBefore(jwtAuthenticationFilterBean(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(exceptionHandlerFilterBean(), JwtAuthenticationFilter.class);
        return http.build();
    }
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilterBean(){
        return new JwtAuthenticationFilter();
    }
    @Bean
    public ExceptionHandlerFilter exceptionHandlerFilterBean(){
        return new ExceptionHandlerFilter();
    }

}