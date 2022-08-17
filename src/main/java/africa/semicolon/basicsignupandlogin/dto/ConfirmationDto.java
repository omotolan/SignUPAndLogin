package africa.semicolon.basicsignupandlogin.dto;

import africa.semicolon.basicsignupandlogin.data.models.ConfirmationToken;
import africa.semicolon.basicsignupandlogin.data.models.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;
@Builder
@Setter
@Getter
public class ConfirmationDto {
    private String token;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private LocalDateTime confirmedAt;
    private User user;


    public static ConfirmationDto packDto(ConfirmationToken confirmationToken){
        return ConfirmationDto.builder()
                .confirmedAt(confirmationToken.getConfirmedAt())
                .createdAt(confirmationToken.getCreatedAt())
                .token(confirmationToken.getToken())
                .user(confirmationToken.getUser())
                .expiresAt(confirmationToken.getExpiresAt())

                .build();
    }
}
