package wint.webchat.controller.user;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import wint.webchat.common.RedisKeys;
import wint.webchat.event.mailEvent.MailPublish;
import wint.webchat.modelDTO.request.AuthLoginDTO;
import wint.webchat.modelDTO.request.AuthSignUpDTO;
import wint.webchat.modelDTO.reponse.ApiResponse;
import wint.webchat.modelDTO.reponse.AuthResponseData;
import wint.webchat.modelDTO.request.ResetPasswordDTO;
import wint.webchat.service.Impl.AuthService;
import wint.webchat.service.Impl.RoleService;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final RoleService roleService;
    private final MailPublish mailPublish;

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
    public ApiResponse<AuthResponseData> signIn(@RequestBody AuthLoginDTO signUpRequest,
                                                HttpServletResponse response) {
        return authService.signIn(signUpRequest, response);
    }

    @PostMapping("/refresh-token")
    public ApiResponse<Map<String, String>> refreshToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        Optional<Cookie> cookie = Arrays.stream(cookies)
                .filter(r -> r.getName().equals(RedisKeys.REFRESH_TOKEN.getValueRedisKey()))
                .findFirst();
        if (cookie.isPresent()) {
            System.out.println(cookie.get().getValue());
            return authService.refreshToken(cookie.get().getValue());
        } else {
            System.out.println("khogn co");
        }
        return null;
    }
    @PostMapping("/forget-password")
    public ResponseEntity<String> forgetPassword(@RequestParam("email") String email) {
        return authService.sendMailResetPassword(email);
    }
    @PostMapping("/reset-password")
    public ApiResponse<String> resetPassword(@Valid @RequestBody(required = false) ResetPasswordDTO resetPasswordDTO ,BindingResult bindingResult) {
        if (resetPasswordDTO == null) {
            return ApiResponse.<String>builder()
                    .success(false)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .error(Map.of("Object","Request body is missing or empty"))
                    .build();
        }
        if(bindingResult.hasErrors()){
            Map<String,String> listError=new HashMap<>();
            bindingResult.getFieldErrors().forEach(e->listError.put(e.getField(),e.getDefaultMessage()));
            return ApiResponse.<String>builder()
                    .data("")
                    .error(listError)
                    .success(false)
                    .code(400)
                    .build();
        }

        return authService.resetPassword(resetPasswordDTO);
    }
    @PostMapping("/sign-in-with-google")
    public ApiResponse<AuthResponseData>  signInWithGoogle(@RequestParam(value = "code") String code,
                                                             HttpServletResponse response) {
        return authService.signInWithGoogle(code,response);
    }

    @GetMapping("/url-login/{type}")
    public ResponseEntity<String> getAuthUrl(@NonNull @PathVariable("type") String type) {
        return authService.getAuthUrl(type);
    }
}
