package wint.webchat.modelDTO.reponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class AuthGoogleResponseDTO {
    private String accessToken;
    private String refreshToken;
}
