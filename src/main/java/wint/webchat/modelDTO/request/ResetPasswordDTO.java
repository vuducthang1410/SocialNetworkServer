package wint.webchat.modelDTO.request;

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
public class ResetPasswordDTO {
    @NotBlank(message = "authorization cannot be left blank")
    private String authorization;
    @Size(min = 10,message = "password must be longer than 10 characters")
    @NotBlank(message = " password cannot be left blank")
    private String password;
    @NotBlank(message = " email cannot be left blank")
    private String email;
}
