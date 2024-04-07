package wint.webchat.controller.user;

import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import wint.webchat.modelDTO.AuthLoginDTO;
import wint.webchat.modelDTO.AuthSignUpDTO;
import wint.webchat.modelDTO.ResponseAuthData;
import wint.webchat.service.Impl.AuthService;
import wint.webchat.service.Impl.RoleService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final RoleService roleService;
    @PostMapping(value = "/signUp")
    public ResponseEntity<String> register(@Valid @RequestBody AuthSignUpDTO authSignUpDTO,
                                           BindingResult bindingResult){
        System.out.println(authSignUpDTO.getUsername());
        bindingResult.getAllErrors().stream().forEach(e-> System.out.println(e.getDefaultMessage()));
        if(bindingResult.hasErrors()){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Validation failed");
        }
        return authService.signUp(authSignUpDTO);
    }
    @PostMapping("/signIn")
    public ResponseEntity<Object> signUp(@RequestBody AuthLoginDTO signUpRequest){
        return ResponseEntity.ok(authService.signIn(signUpRequest));
    }
    @PostMapping("/refresh-token")
    public ResponseEntity<String> refreshToken(@RequestParam("accessToken")String accessToken,
                                               @RequestParam("refreshToken")String refreshToken){
        return ResponseEntity.ok("");
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
    @GetMapping("/sign-in-with-google")
    public ResponseEntity<ResponseAuthData> signInWithGoogle(@RequestParam(value = "code")String code){
        return null;
    }
    @GetMapping("/url-login/{type}")
    public ResponseEntity<String> getAuthUrl(@NonNull @PathVariable("type")String type){
        return authService.getAuthUrl(type);
    }
}
