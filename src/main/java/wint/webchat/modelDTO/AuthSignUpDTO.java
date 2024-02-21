package wint.webchat.modelDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthSignUpDTO {
    private String fullname;
    private String username;
    private String password;
    private String email;
}