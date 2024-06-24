package wint.webchat.service.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import wint.webchat.common.AccountType;
import wint.webchat.common.AuthEventType;
import wint.webchat.common.RedisKeys;
import wint.webchat.common.StatusCode;
import wint.webchat.entities.user.Role;
import wint.webchat.entities.user.User;
import wint.webchat.event.authEvent.AuthPublish;
import wint.webchat.event.authEvent.AuthSubscriber;
import wint.webchat.event.mailEvent.MailPublish;
import wint.webchat.google.GoogleAuth;
import wint.webchat.mapper.JsonMapper;
import wint.webchat.modelDTO.PubSubDTO.AuthRedisDTO;
import wint.webchat.modelDTO.PubSubDTO.PubSubMessage;
import wint.webchat.modelDTO.reponse.ApiResponse;
import wint.webchat.modelDTO.reponse.AuthGoogleResponseDTO;
import wint.webchat.modelDTO.reponse.AuthResponseData;
import wint.webchat.modelDTO.reponse.UserInfoGoogleResponseDTO;
import wint.webchat.modelDTO.request.AuthLoginDTO;
import wint.webchat.modelDTO.request.AuthSignUpDTO;
import wint.webchat.modelDTO.request.ResetPasswordDTO;
import wint.webchat.redis.AuthRedisService;
import wint.webchat.redis.RedisService;
import wint.webchat.repositories.IRoleRepository;
import wint.webchat.repositories.IUserRepositoryJPA;
import wint.webchat.repositories.Impl.UserRoleRepositoryImpl;
import wint.webchat.security.CustomUserDetail;
import wint.webchat.util.TokenGenerator;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtServiceImpl jwtService;
    private final PasswordEncoder passwordEncoder;
    private final IUserRepositoryJPA userRepositoryJPA;
    private final UserRoleRepositoryImpl userRoleRepository;
    private final IRoleRepository roleRepository;
    private final GoogleAuth googleAuth;
    private final AuthSubscriber authSubscriber;
    private final MailPublish mailPublish;
    private final RedisService redisService;
    private final AuthenticationManager authenticationManager;
    private final AuthPublish authPublish;
    private final JsonMapper jsonMapper;
    private final AuthRedisService authRedisService;

    public ApiResponse<AuthResponseData> signIn(AuthLoginDTO authLoginDTO, HttpServletResponse response) {
        try {
            Authentication authentication = UsernamePasswordAuthenticationToken.unauthenticated(authLoginDTO.getUsername(), authLoginDTO.getPassword());
            Authentication authenticationResponse = authenticationManager.authenticate(authentication);
            CustomUserDetail obj = (CustomUserDetail) authenticationResponse.getPrincipal();
            return ApiResponse.<AuthResponseData>builder()
                    .data(getResponseAuthData(obj, response))
                    .success(true)
                    .code(200)
                    .message("Login success")
                    .error(Map.of())
                    .build();
        } catch (AuthenticationException ae) {
            return ApiResponse.<AuthResponseData>builder()
                    .data(null)
                    .message("Login fail")
                    .code(400)
                    .error(Map.of("password", "Password isn't correct"))
                    .success(false)
                    .build();
        } catch (Exception e) {
            return ApiResponse.<AuthResponseData>builder()
                    .data(null)
                    .message("Login fail")
                    .code(400)
                    .error(Map.of("server", "server error"))
                    .success(false)
                    .build();
        }
    }

    public ResponseEntity<String> signUp(AuthSignUpDTO authSignUp) {
        Optional<User> userCheck = userRepositoryJPA.findUsersByUserName(authSignUp.getUsername());
        if (userCheck.isEmpty()) {
            User user = new User(authSignUp.getUsername()
                    , passwordEncoder.encode(authSignUp.getPassword())
                    , authSignUp.getFullname(),
                    AccountType.SYSTEM.getAccountType()
            );
            List<Role> list = roleRepository.findByRoleName("ROLE_USER").stream().collect(Collectors.toList());
            if (list.isEmpty()) return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error");
            return userRoleRepository.addRoleForUser(user, list);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("username đã tồn tại");
        }
    }

    public ApiResponse<Map<String, String>> refreshToken(String refreshToken) {
        try {
            if (refreshToken.length() == 0) throw new Exception();
            jwtService.isTokenExpiration(refreshToken);
            String username = jwtService.getUsernameFromToken(refreshToken);
            if (authRedisService.isRefreshTokenContainRedis(refreshToken, username)) {
                if (!jwtService.isTokenExpiration(refreshToken)) {
                    Collection<GrantedAuthority> authorities = authRedisService.getGrantedAuthoritiesWithRefreshToken(username);
                    String newAccessToken = jwtService.generateAccessToken(new HashMap<>(), username, authorities);
                    AuthRedisDTO authRedisDTO = AuthRedisDTO.builder()
                            .accessToken(newAccessToken)
                            .refreshToken(refreshToken)
                            .username(username)
                            .authorities(authorities)
                            .build();
                    PubSubMessage<AuthRedisDTO> pubSubMessage = PubSubMessage
                            .<AuthRedisDTO>builder()
                            .messageId("")
                            .timeMessageCreate(new Date().getTime())
                            .attributes(Map.of())
                            .evenType(AuthEventType.REFRESH_ACCESS_TOKEN.getGetAuthEventType())
                            .payload(authRedisDTO)
                            .build();
                    authPublish.publishEvent(jsonMapper.objectToJson(pubSubMessage));
                    return ApiResponse.<Map<String, String>>builder()
                            .error(Map.of())
                            .code(StatusCode.SUCCESS_CODE)
                            .message("Successfully")
                            .data(Map.of("accessToken", newAccessToken))
                            .success(true)
                            .build();
                } else {
                    throw new UnsupportedJwtException("");
                }
            } else
                throw new Exception();
        } catch (ExpiredJwtException eje) {
            return ApiResponse.<Map<String, String>>builder()
                    .error(Map.of("refreshToken", "REFRESH_TOKEN_EXPIRATION"))
                    .code(StatusCode.REFRESH_TOKEN_EXPIRATION_CODE)
                    .message("Failure")
                    .data(Map.of())
                    .success(false)
                    .build();
        } catch (UnsupportedJwtException uje) {
            return ApiResponse.<Map<String, String>>builder()
                    .error(Map.of("refreshToken", "REFRESH_TOKEN_UNSUPPORTED"))
                    .code(StatusCode.TOKEN_NOT_VALID_CODE)
                    .message("Failure")
                    .data(Map.of())
                    .success(false)
                    .build();
        } catch (Exception e) {
            return ApiResponse.<Map<String, String>>builder()
                    .error(Map.of("refreshToken", "SERVER ERROR"))
                    .code(StatusCode.SERVER_ERROR)
                    .message("Failure")
                    .data(Map.of())
                    .success(false)
                    .build();
        }
    }

    public ApiResponse<AuthResponseData> signInWithGoogle(String authCode, HttpServletResponse response) {
        try {
            AuthGoogleResponseDTO responseAuthData = googleAuth.signIn(authCode);
            UserInfoGoogleResponseDTO userInfo = googleAuth.extractTokenGoogle(responseAuthData.getAccessToken());
            Optional<User> userFromDb = userRepositoryJPA.findUsersByUserName(userInfo.getEmail());
            if (userFromDb.isEmpty()) {
                User userNew = new User(userInfo.getEmail(), passwordEncoder.encode(""),
                        userInfo.getName(), userInfo.getEmail(), userInfo.getPicture(), AccountType.GOOGLE.getAccountType());
                List<Role> list = roleRepository.findByRoleName("ROLE_USER").stream().collect(Collectors.toList());
                if (list.isEmpty())
                    throw new Exception();
                userRoleRepository.addRoleForUser(userNew, list);
                userFromDb = Optional.of(userNew);
            }
            CustomUserDetail userDetail = userFromDb.map(CustomUserDetail::new).orElseThrow();
            return ApiResponse.<AuthResponseData>builder()
                    .data(getResponseAuthData(userDetail, response))
                    .success(true)
                    .code(200)
                    .message("Login success")
                    .error(Map.of())
                    .build();
        } catch (Exception e) {
            return ApiResponse.<AuthResponseData>builder()
                    .data(null)
                    .message("Login fail")
                    .code(400)
                    .error(Map.of("server", "server error"))
                    .success(false)
                    .build();
        }
    }

    public ResponseEntity<String> getAuthUrl(String type) {
        try {
            if (type.equalsIgnoreCase("GOOGLE")) {
                return ResponseEntity.ok(googleAuth.getAuthUrl());
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Do not identify type login!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error" + e.getMessage());
        }
    }

    private AuthResponseData getResponseAuthData(CustomUserDetail userDetail, HttpServletResponse response) {
        var accessToken = jwtService.generateAccessToken(new HashMap<>(), userDetail.getUsername(), (Collection<GrantedAuthority>) userDetail.getAuthorities());
        var refreshToken = jwtService.generateRefreshToken(userDetail.getUsername());
        Cookie cookie = new Cookie(RedisKeys.REFRESH_TOKEN.getValueRedisKey(), refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setMaxAge(86400000);
        response.addCookie(cookie);
        var user = userDetail.getUser();
        addMessageToAuthQueue(user.getUserName(), accessToken, refreshToken, (Collection<GrantedAuthority>) userDetail.getAuthorities());
        AuthResponseData authResponseData = AuthResponseData.builder()
                .userId(user.getId())
                .urlAvatar(user.getUrlAvatar())
                .fullName(user.getFullName())
                .accessToken(accessToken)
                .role((Collection<GrantedAuthority>) userDetail.getAuthorities())
                .message("Login success")
                .build();
        return authResponseData;
    }

    public ResponseEntity<String> sendMailResetPassword(String email) {
        String token = TokenGenerator.generateToken(150);
        mailPublish.pushEventToQueue(email, token);
        return ResponseEntity.ok("Password reset email sent successfully!!");
    }

    public ApiResponse<String> resetPassword(ResetPasswordDTO resetPasswordDTO) {
        try {
            String authorizationToken = redisService.getValue(resetPasswordDTO.getEmail());
            if (authorizationToken.equalsIgnoreCase(resetPasswordDTO.getAuthorization())) {
                userRepositoryJPA.updatePasswordByEmail(resetPasswordDTO.getEmail(), passwordEncoder.encode(resetPasswordDTO.getPassword()));
                return ApiResponse.<String>builder()
                        .data("Reset password is successfully")
                        .error(Map.of())
                        .success(true)
                        .code(200)
                        .build();
            } else {
                return ApiResponse.<String>builder()
                        .data("")
                        .error(Map.of("AuthorizationToken", "Token is not valid"))
                        .success(false)
                        .code(400)
                        .build();
            }
        } catch (Exception e) {
            return ApiResponse.<String>builder()
                    .data("")
                    .error(Map.of("Server", "Server is error"))
                    .success(false)
                    .code(400)
                    .build();
        }
    }
    public void logout(String refreshToken) {
        publishLogoutEvent(refreshToken, AuthEventType.LOGOUT);
    }

    public void logoutAll(String refreshToken) {
        publishLogoutEvent(refreshToken, AuthEventType.LOGOUT_ALL);
    }

    private void publishLogoutEvent(String refreshToken, AuthEventType eventType) {
        String username = jwtService.getUsernameFromToken(refreshToken);
        AuthRedisDTO authRedisDTO = AuthRedisDTO.builder()
                .username(username)
                .refreshToken(refreshToken)
                .accessToken("")
                .authorities(Collections.emptyList())
                .build();

        PubSubMessage<AuthRedisDTO> pubSubMessage = PubSubMessage.<AuthRedisDTO>builder()
                .messageId("")
                .timeMessageCreate(new Date().getTime())
                .payload(authRedisDTO)
                .attributes(Collections.emptyMap())
                .evenType(eventType.getGetAuthEventType())
                .build();

        try {
            authPublish.publishEvent(jsonMapper.objectToJson(pubSubMessage));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    private void addMessageToAuthQueue(String username,
                                       String accessToken,
                                       String refreshToken,
                                       Collection<GrantedAuthority> authorities) {
        AuthRedisDTO authRedisDTO = AuthRedisDTO.builder()
                .username(username)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .authorities(authorities)
                .build();
        PubSubMessage<AuthRedisDTO> pubSubMessage = PubSubMessage.<AuthRedisDTO>builder()
                .messageId("")
                .timeMessageCreate(new Date().getTime())
                .payload(authRedisDTO)
                .attributes(Map.of())
                .evenType(AuthEventType.SAVE_TOKEN_LOGIN.getGetAuthEventType())
                .build();
        try {
            authPublish.publishEvent(jsonMapper.objectToJson(pubSubMessage));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
