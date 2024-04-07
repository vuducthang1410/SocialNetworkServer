package wint.webchat.modelDTO;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;

@Getter
@Setter
@AllArgsConstructor
public class AuthSignUpDTO {
    @NotBlank
    @Size(min = 5,message = "fullname must be longer than 5 characters")
    private String fullname;
    @NotBlank(message = "username must not be blank")
    @Size(min = 5,message = "username must be longer than 5 characters")
    private String username;
    @NotBlank(message ="password must not be blank")
    @Size(min = 8,message = "password must be longer than 5 characters")
    private String password;
}
