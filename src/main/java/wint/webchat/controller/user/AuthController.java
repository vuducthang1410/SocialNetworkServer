package wint.webchat.controller.user;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import wint.webchat.enums.RedisKeys;
import wint.webchat.modelDTO.AuthLoginDTO;
import wint.webchat.modelDTO.AuthSignUpDTO;
import wint.webchat.modelDTO.AuthResponseData;
import wint.webchat.service.Impl.AuthService;
import wint.webchat.service.Impl.RoleService;

import java.util.Arrays;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final RoleService roleService;

    @PostMapping(value = "/signUp")
    public ResponseEntity<String> register(@Valid @RequestBody AuthSignUpDTO authSignUpDTO,
                                           BindingResult bindingResult) {
        System.out.println(authSignUpDTO.getUsername());
        bindingResult.getAllErrors().stream().forEach(e -> System.out.println(e.getDefaultMessage()));
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Validation failed");
        }
        return authService.signUp(authSignUpDTO);
    }

    @PostMapping("/signIn")
    public ResponseEntity<Object> signIn(@RequestBody AuthLoginDTO signUpRequest,
                                         HttpServletResponse response) {
        return authService.signIn(signUpRequest, response);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<String> refreshToken(@RequestParam("accessToken") String accessToken,
                                               HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        Optional<Cookie> cookie = Arrays.stream(cookies)
                .filter(r -> r.getName().equals(RedisKeys.REFRESH_TOKEN.getValueRedisKey()))
                .findFirst();
        if (cookie.isPresent()) {
            System.out.println(cookie.get().getValue());
            return authService.refreshToken(cookie.get().getValue(), accessToken);
        } else {
            System.out.println("khogn co");
        }
        return null;
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestParam("password") String password) {
        return null;
    }

    @PostMapping("/forget-password")
    public ResponseEntity<String> forgetPassword() {
        return null;
    }

    @GetMapping("/sendmail")
    public void sendmail() {
        roleService.sendEmail("123xxthang@gmail.com", "Hello", "from hygger");
    }

    @PostMapping("/sign-in-with-google")
    public ResponseEntity<Object> signInWithGoogle(@RequestParam(value = "code") String code,
                                                             HttpServletResponse response) {
        return authService.signInWithGoogle(code,response);
    }

    @GetMapping("/url-login/{type}")
    public ResponseEntity<String> getAuthUrl(@NonNull @PathVariable("type") String type) {
        return authService.getAuthUrl(type);
    }
}
