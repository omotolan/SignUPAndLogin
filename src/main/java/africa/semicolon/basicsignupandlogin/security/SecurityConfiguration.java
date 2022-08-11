package africa.semicolon.basicsignupandlogin.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfiguration {

    private final UnAuthorizedEntryPoint unAuthorizedEntryPoint;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeHttpRequests(authorize -> {
                    try {
                        authorize.antMatchers("/**/user/signup", "/**/user/login").permitAll()
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
//        http.addFilterBefore(jwtAuthenticationFilterBean(), UsernamePasswordAuthenticationFilter.class);
//        http.addFilterBefore(exceptionHandlerFilterBean(), JwtAuthenticationFilter.class);
        return http.build();
    }

}