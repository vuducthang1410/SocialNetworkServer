package wint.webchat.modelDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthLoginDTO {
    @NotBlank(message = "username must not be blank")
    @Size(min = 5,message = "username must be longer than 5 characters")
    private String username;
    @NotBlank(message ="password must not be blank")
    @Size(min = 8,message = "password must be longer than 5 characters")
    private String password;
}
