package wint.webchat.controller.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
import wint.webchat.modelDTO.request.AuthLoginDTO;
import wint.webchat.modelDTO.request.AuthSignUpDTO;
import wint.webchat.modelDTO.request.ResetPasswordDTO;
import wint.webchat.service.Impl.AuthService;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping(value = "/sign-up")
    public CompletableFuture<ResponseData<Map<String, Object>>> register(
            @Valid @RequestBody AuthSignUpDTO authSignUpDTO,
            BindingResult bindingResult,
            @NonNull @RequestHeader(Constant.TRANSACTION_ID_KEY) String transactionId) {
        return CompletableFuture.completedFuture(ResponseData.createResponse(
                authService.signUp(authSignUpDTO, transactionId, bindingResult))
        );
    }

    @PostMapping("/sign-in")
    public CompletableFuture<ResponseData<Map<String, Object>>> signIn(
            @RequestBody AuthLoginDTO signUpRequest,
            HttpServletResponse response,
            BindingResult bindingResult,
            @NonNull @RequestHeader(Constant.TRANSACTION_ID_KEY) String transactionId) {
        return CompletableFuture.completedFuture(ResponseData.createResponse(
                authService.signIn(signUpRequest, response, bindingResult, transactionId))
        );
    }

    @PostMapping("/refresh-token")
    public CompletableFuture<ResponseData<Map<String, Object>>> refreshToken(
            HttpServletRequest request,
            @NonNull @RequestHeader(Constant.TRANSACTION_ID_KEY) String transactionId
    ) {
        return CompletableFuture.completedFuture(ResponseData.createResponse(
                authService.refreshToken(request, transactionId))
        );
    }

    @PostMapping("/forget-password")
    public CompletableFuture<ResponseData<Map<String, Object>>> forgetPassword(
            @RequestParam("email") String email,
            @NonNull @RequestHeader(Constant.TRANSACTION_ID_KEY) String transactionId) {
        return CompletableFuture.completedFuture(
                ResponseData.createResponse(
                        authService.sendMailResetPassword(email, transactionId)
                ));
    }

    @PostMapping("/reset-password")
    public CompletableFuture<ResponseData<Map<String, Object>>> resetPassword(
            @Valid @RequestBody(required = false) ResetPasswordDTO resetPasswordDTO,
            BindingResult bindingResult,
            @NonNull @RequestHeader(Constant.TRANSACTION_ID_KEY) String transactionId) {
        return CompletableFuture.completedFuture(
                ResponseData.createResponse(
                        authService.resetPassword(resetPasswordDTO, bindingResult, transactionId)
                ));
    }

    @PostMapping("/sign-in-with-google")
    public CompletableFuture<ResponseData<Map<String, Object>>> signInWithGoogle(
            @RequestParam(value = "code") String code,
            HttpServletResponse response,
            @NonNull @RequestHeader(Constant.TRANSACTION_ID_KEY) String transactionId) {
        return CompletableFuture.completedFuture(
                ResponseData.createResponse(authService.signInWithGoogle(code, response,transactionId)));
    }

    @GetMapping("/url-login/{type}")
    public CompletableFuture<ResponseData<Map<String, Object>>> getAuthUrl(
            @NonNull @PathVariable("type") String type,
            @NonNull @RequestHeader(Constant.TRANSACTION_ID_KEY) String transactionId) {
        return CompletableFuture.completedFuture(ResponseData.createResponse(authService.getAuthUrl(type,transactionId)));
    }

    @PostMapping("/logout")
    public CompletableFuture<ResponseData<Map<String, Object>>> logout(
            @CookieValue("REFRESH_TOKEN") String refreshToken,
            @NonNull @RequestHeader(Constant.TRANSACTION_ID_KEY) String transactionId
    ) {

        return CompletableFuture.completedFuture(ResponseData.createResponse(authService.logout(refreshToken, transactionId)));
    }

    @PostMapping("/logout-all")
    public CompletableFuture<ResponseData<Map<String, Object>>> logoutAll(
            @CookieValue("REFRESH_TOKEN") String refreshToken,
            @NonNull @RequestHeader(Constant.TRANSACTION_ID_KEY) String transactionId
    ) {
        return CompletableFuture.completedFuture(ResponseData.createResponse(authService.logoutAll(refreshToken, transactionId)));
    }
}
