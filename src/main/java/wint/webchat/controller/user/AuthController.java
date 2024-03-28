package wint.webchat.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wint.webchat.modelDTO.AuthLoginDTO;
import wint.webchat.modelDTO.AuthSignUpDTO;
import wint.webchat.modelDTO.ResponseData;
import wint.webchat.service.Impl.AuthService;
import wint.webchat.service.Impl.RoleService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final RoleService roleService;
    @PostMapping("/signUp")
    public ResponseEntity<String> register(@RequestBody AuthSignUpDTO authSignUpDTO){
        return authService.signUp(authSignUpDTO);
    }
    @PostMapping("/signIn")
    public ResponseEntity<ResponseData> signUp(@RequestBody AuthLoginDTO signUpRequest){
        return ResponseEntity.ok(authService.signIn(signUpRequest));
    }
    @PostMapping("/refresh-token")
    public ResponseEntity<String> refreshToken(@RequestParam("accessToken")String accessToken,
                                               @RequestParam("refreshToken")String refreshToken){
        return null;
    }
    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestParam("password")String password){
        return null;
    }
    @PostMapping("/forget-password")
    public ResponseEntity<String> forgetPassword(){
        return null;
    }
    @GetMapping("/sendmail")
    public void sendmail(){
        roleService.sendEmail("123xxthang@gmail.com","Hello","from hygger");
    }
}
