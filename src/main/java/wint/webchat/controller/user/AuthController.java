package wint.webchat.controller.user;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wint.webchat.common.Constant;
import wint.webchat.common.ResponseData;
import wint.webchat.modelDTO.reponse.ApiResponse;
import wint.webchat.modelDTO.reponse.AuthResponseData;
import wint.webchat.modelDTO.request.AuthLoginDTO;
import wint.webchat.modelDTO.request.AuthSignUpDTO;
import wint.webchat.modelDTO.request.ResetPasswordDTO;
import wint.webchat.service.Impl.AuthService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping(value = "/sign-up")
    public CompletableFuture<ResponseData<Map<String, Object>>> register(@Valid @RequestBody AuthSignUpDTO authSignUpDTO,
                                                                         BindingResult bindingResult,
                                                                         @NonNull @RequestHeader(Constant.TRANSACTION_ID_KEY) String transactionId) {
        return CompletableFuture.completedFuture(ResponseData.createResponse(authService.signUp(authSignUpDTO, transactionId, bindingResult)));
    }

    @PostMapping("/sign-in")
    public CompletableFuture<ResponseData<Map<String, Object>>> signIn(@RequestBody AuthLoginDTO signUpRequest,
                                                                       HttpServletResponse response,
                                                                       BindingResult bindingResult,
                                                                       @NonNull @RequestHeader(Constant.TRANSACTION_ID_KEY) String transactionId) {
        return CompletableFuture.completedFuture(ResponseData.createResponse(authService.signIn(signUpRequest, response,bindingResult,transactionId)));
    }

    @PostMapping("/refresh-token")
    public ApiResponse<Map<String, String>> refreshToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            Optional<Cookie> cookie = Arrays.stream(cookies)
                    .filter(r -> r.getName().equals(Constant.RedisKeys.REFRESH_TOKEN.getValueRedisKey()))
                    .findFirst();
            if (cookie.isPresent()) {
                return authService.refreshToken(cookie.get().getValue());
            } else {
                return ApiResponse.<Map<String, String>>builder()
                        .code(HttpStatus.FORBIDDEN.value())
                        .error(Map.of("cookie", "not found refresh token in cookie"))
                        .build();
            }
        }
        return ApiResponse.<Map<String, String>>builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .error(Map.of("cookie", "not found cookie"))
                .build();
    }

    @PostMapping("/forget-password")
    public CompletableFuture<ResponseEntity<String>> forgetPassword(@RequestParam("email") String email) {
        return CompletableFuture.completedFuture(authService.sendMailResetPassword(email));
    }

    @PostMapping("/reset-password")
    public ApiResponse<String> resetPassword(@Valid @RequestBody(required = false) ResetPasswordDTO resetPasswordDTO, BindingResult bindingResult) {
        if (resetPasswordDTO == null) {
            return ApiResponse.<String>builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .error(Map.of("Object", "Request body is missing or empty"))
                    .build();
        }
        if (bindingResult.hasErrors()) {
            Map<String, String> listError = new HashMap<>();
            bindingResult.getFieldErrors().forEach(e -> listError.put(e.getField(), e.getDefaultMessage()));
            return ApiResponse.<String>builder()
                    .data("")
                    .error(listError)
                    .code(400)
                    .build();
        }

        return authService.resetPassword(resetPasswordDTO);
    }

    @PostMapping("/sign-in-with-google")
    public ApiResponse<AuthResponseData> signInWithGoogle(@RequestParam(value = "code") String code,
                                                          HttpServletResponse response) {
        return authService.signInWithGoogle(code, response);
    }

    @GetMapping("/url-login/{type}")
    public ResponseEntity<String> getAuthUrl(@NonNull @PathVariable("type") String type) {
        return authService.getAuthUrl(type);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@CookieValue("REFRESH_TOKEN") String refreshToken) {
        authService.logout(refreshToken);
        return ResponseEntity.ok("successfully");
    }

    @PostMapping("/logout-all")
    public ResponseEntity<String> logoutAll(@CookieValue("REFRESH_TOKEN") String refreshToken) {
        authService.logoutAll(refreshToken);
        return ResponseEntity.ok("successfully");
    }
}
