package wint.webchat.modelDTO.PubSubDTO;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class MailResetPasswordDTO {
    private String email;
    private String token;
    @JsonCreator
    public MailResetPasswordDTO(@JsonProperty("email") String email, @JsonProperty("token") String token) {
        this.email = email;
        this.token = token;
    }
}
