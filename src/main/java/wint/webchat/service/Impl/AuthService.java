package wint.webchat.service.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import wint.webchat.common.Constant;
import wint.webchat.common.ResponseCode;
import wint.webchat.entities.user.Role;
import wint.webchat.entities.user.User;
import wint.webchat.event.authEvent.AuthPublish;
import wint.webchat.event.mailEvent.MailPublish;
import wint.webchat.google.GoogleAuth;
import wint.webchat.mapper.JsonMapper;
import wint.webchat.modelDTO.PubSubDTO.AuthRedisDTO;
import wint.webchat.modelDTO.PubSubDTO.PubSubMessage;
import wint.webchat.modelDTO.Result;
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
import wint.webchat.repositories.RoleRepositoryJPA;
import wint.webchat.util.TokenGenerator;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
    private final MailPublish mailPublish;
    private final RedisService redisService;
    private final AuthPublish authPublish;
    private final JsonMapper jsonMapper;
    private final RoleRepositoryJPA roleRepositoryJPA;
    private final AuthRedisService authRedisService;
    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    public Map<String, Object> signIn(AuthLoginDTO authLoginDTO,
                                      HttpServletResponse response,
                                      BindingResult bindingResult,
                                      String transactionId) {
        Map<String, Object> responseData = new HashMap<>();
        Result result = Result.Ok();
        try {
            Optional<User> userOptional = userRepositoryJPA.findByUserName(authLoginDTO.getUsername());
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                boolean success = passwordEncoder.matches(authLoginDTO.getPassword(), user.getPasswordEncrypt());
                if (success) {
//                    return ApiResponse.<AuthResponseData>builder()
//                            .data(getResponseAuthData(user, response))
//                            .code(200)
//                            .error(Map.of())
//                            .build();
                }
            }else
                result=new Result()
        } catch (Exception e) {
            log.error(Constant.MESSAGE_LOG,transactionId, e.getMessage());
            result = Result.SYSTEM_ERR();
        }
        responseData.put(Constant.RESPONSE_KEY.RESULT, result);
        return responseData;
    }

    @Transactional
    public Map<String, Object> signUp(AuthSignUpDTO authSignUp, String transactionId, BindingResult bindingResult) {
        Map<String, Object> responseData = new HashMap<>();
        Result result = Result.Ok();
        try {
            if (bindingResult.hasErrors()) {
                result = new Result(ResponseCode.DATA_REGISTER_ACCOUNT_MISSING.getCode(), false, ResponseCode.DATA_REGISTER_ACCOUNT_MISSING.getMessage());
                responseData.put(Constant.RESPONSE_KEY.RESULT, result);
                return responseData;
            }
            Optional<User> userCheck = userRepositoryJPA.findByUserName(authSignUp.getUsername());
            if (userCheck.isEmpty()) {
                User user = new User(authSignUp.getUsername()
                        , passwordEncoder.encode(authSignUp.getPassword())
                        , authSignUp.getFirstname(),
                        authSignUp.getLastname(),
                        Constant.AccountType.SYSTEM.getAccountType()
                );
                List<Role> list = roleRepository.findByRoleName("ROLE_USER").stream().collect(Collectors.toList());
                if (list.isEmpty())
                    result = new Result(ResponseCode.ROLE_NOT_EXITS.getCode(), false, ResponseCode.ROLE_NOT_EXITS.getMessage());
                else
                    userRoleRepository.addRoleForUser(user, list);
            } else {
                result = new Result(ResponseCode.ACCOUNT_ALREADY_EXISTS.getCode(), false, ResponseCode.ACCOUNT_ALREADY_EXISTS.getMessage());
            }
        } catch (Exception e) {
            log.error(Constant.MESSAGE_LOG, transactionId, "Xảy ra ngoại lệ khi thực hiện đăng ký tài khoản", e.getMessage());
            result = Result.SYSTEM_ERR();
        }
        responseData.put(Constant.RESPONSE_KEY.RESULT, result);
        return responseData;
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
                            .evenType(Constant.AuthEventType.REFRESH_ACCESS_TOKEN.getGetAuthEventType())
                            .payload(authRedisDTO)
                            .build();
                    authPublish.publishEvent(jsonMapper.objectToJson(pubSubMessage));
                    return ApiResponse.<Map<String, String>>builder()
                            .error(Map.of())
                            .code(Constant.StatusCode.SUCCESS_CODE)
                            .data(Map.of("accessToken", newAccessToken))
                            .build();
                } else {
                    throw new UnsupportedJwtException("");
                }
            } else
                throw new Exception();
        } catch (Exception e) {
            log.error("Xảy ra ngoại lệ khi thưc hiện refresh token! Root cause: {}", e.getMessage());
            return ApiResponse.<Map<String, String>>builder()
                    .error(Map.of("refreshToken", "SERVER ERROR"))
                    .code(Constant.StatusCode.SERVER_ERROR)
                    .data(Map.of())
                    .build();
        }
    }

    public ApiResponse<AuthResponseData> signInWithGoogle(String authCode, HttpServletResponse response) {
        try {
            AuthGoogleResponseDTO responseAuthData = googleAuth.signIn(authCode);
            UserInfoGoogleResponseDTO userInfo = googleAuth.extractTokenGoogle(responseAuthData.getAccessToken());
            Optional<User> userFromDb = userRepositoryJPA.findByUserName(userInfo.getEmail());
            if (userFromDb.isEmpty()) {
                User userNew = new User(userInfo.getEmail(), passwordEncoder.encode(""),
                        userInfo.getName(), "", userInfo.getEmail(), userInfo.getPicture(), Constant.AccountType.GOOGLE.getAccountType());
                List<Role> list = roleRepository.findByRoleName("ROLE_USER").stream().collect(Collectors.toList());
                if (list.isEmpty())
                    throw new Exception();
                userRoleRepository.addRoleForUser(userNew, list);
            }
            return ApiResponse.<AuthResponseData>builder()
                    .code(200)
                    .error(Map.of())
                    .build();
        } catch (Exception e) {
            return ApiResponse.<AuthResponseData>builder()
                    .data(null)
                    .code(400)
                    .error(Map.of("server", "server error"))
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

    private AuthResponseData getResponseAuthData(User user, HttpServletResponse response) {
        Collection<GrantedAuthority> listAuthorities = roleRepositoryJPA.findRoleByUsername(user.getUserName()).stream().map(e -> new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return e.getRoleName();
            }
        }).collect(Collectors.toList());
        var accessToken = jwtService.generateAccessToken(new HashMap<>(), user.getUserName(), listAuthorities);
        var refreshToken = jwtService.generateRefreshToken(user.getUserName());
        Cookie cookie = new Cookie(Constant.RedisKeys.REFRESH_TOKEN.getValueRedisKey(), refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setMaxAge(86400000);
        response.addCookie(cookie);
        addMessageToAuthQueue(user.getUserName(), accessToken, refreshToken, listAuthorities);
        AuthResponseData authResponseData = AuthResponseData.builder()
                .userId(user.getId())
                .urlAvatar(user.getUrlAvatar())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .accessToken(accessToken)
                .isComplete(user.getIsComplete())
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
            String authorizationToken = redisService.getValueAndRemove(resetPasswordDTO.getEmail());
            if (authorizationToken.equalsIgnoreCase(resetPasswordDTO.getAuthorization())) {
                userRepositoryJPA.updatePasswordByEmail(resetPasswordDTO.getEmail(), passwordEncoder.encode(resetPasswordDTO.getPassword()));
                return ApiResponse.<String>builder()
                        .data("Reset password is successfully")
                        .error(Map.of())
                        .code(200)
                        .build();
            } else {
                return ApiResponse.<String>builder()
                        .data("")
                        .error(Map.of("AuthorizationToken", "Token is not valid"))
                        .code(400)
                        .build();
            }
        } catch (Exception e) {
            return ApiResponse.<String>builder()
                    .data("")
                    .error(Map.of("Server", "Server is error"))
                    .code(400)
                    .build();
        }
    }

    public void logout(String refreshToken) {
        publishLogoutEvent(refreshToken, Constant.AuthEventType.LOGOUT);
    }

    public void logoutAll(String refreshToken) {
        publishLogoutEvent(refreshToken, Constant.AuthEventType.LOGOUT_ALL);
    }

    private void publishLogoutEvent(String refreshToken, Constant.AuthEventType eventType) {
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
                .evenType(Constant.AuthEventType.SAVE_TOKEN_LOGIN.getGetAuthEventType())
                .build();
        try {
            authPublish.publishEvent(jsonMapper.objectToJson(pubSubMessage));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
