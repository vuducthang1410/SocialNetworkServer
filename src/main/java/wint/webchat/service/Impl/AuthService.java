package wint.webchat.service.Impl;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
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
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import wint.webchat.common.Constant;
import wint.webchat.common.ResponseCode;
import wint.webchat.entities.user.Role;
import wint.webchat.entities.user.User;
import wint.webchat.event.mailEvent.MailPublish;
import wint.webchat.google.GoogleAuth;
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

import java.util.Arrays;
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
            if (bindingResult.hasErrors()) {
                result = new Result(ResponseCode.DATA_LOGIN_MISSING.getCode(), false, ResponseCode.DATA_LOGIN_MISSING.getMessage());
                responseData.put(Constant.RESPONSE_KEY.RESULT, result);
                return responseData;
            }
            Optional<User> userOptional = userRepositoryJPA.findByUserName(authLoginDTO.getUsername());
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                if (passwordEncoder.matches(authLoginDTO.getPassword(), user.getPasswordEncrypt())) {
                    var userInfo = getResponseAuthData(user, response);
                    responseData.put(Constant.RESPONSE_KEY.DATA, userInfo);
                } else {
                    result = new Result(
                            ResponseCode.PASSWORD_NOT_CORRECT.getCode(),
                            false,
                            ResponseCode.PASSWORD_NOT_CORRECT.getMessage());
                }
            } else
                result = new Result(
                        ResponseCode.ACCOUNT_NOT_EXITS.getCode(),
                        false,
                        ResponseCode.ACCOUNT_NOT_EXITS.getMessage());
        } catch (Exception e) {
            log.error(Constant.MESSAGE_LOG, transactionId, "Lỗi khi thực hiện đang nhập", e.getMessage());
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

    public Map<String, Object> refreshToken(HttpServletRequest request, String transactionId) {
        Map<String, Object> responseData = new HashMap<>();
        Result result = Result.Ok();
        try {
            //todo kiểm tra cookie có dữ liệu không
            Cookie[] cookies = request.getCookies();
            if (cookies == null) {
                result = new Result(
                        ResponseCode.COOKIE_NOT_EXIST.getCode(),
                        false,
                        ResponseCode.COOKIE_NOT_EXIST.getMessage()
                );
                responseData.put(Constant.RESPONSE_KEY.RESULT, result);
                return responseData;
            }
            Optional<Cookie> cookie = Arrays.stream(cookies)
                    .filter(r -> r.getName().equals(Constant.RedisKeys.REFRESH_TOKEN.getValueRedisKey()))
                    .findFirst();
            if (cookie.isEmpty()) {
                result = new Result(
                        ResponseCode.REFRESH_TOKEN_COOKIE_MISSING.getCode(),
                        false,
                        ResponseCode.REFRESH_TOKEN_COOKIE_MISSING.getMessage()
                );
                responseData.put(Constant.RESPONSE_KEY.RESULT, result);
                return responseData;
            }
            //todo: kiểm tra có cookie có dữ liệu không
            String refreshToken = cookie.get().getValue();
            if (!StringUtils.hasText(refreshToken)) {
                result = new Result(
                        ResponseCode.REFRESH_TOKEN_COOKIE_MISSING.getCode(),
                        false,
                        ResponseCode.REFRESH_TOKEN_COOKIE_MISSING.getMessage()
                );
                responseData.put(Constant.RESPONSE_KEY.RESULT, result);
                return responseData;
            }
            //verify thông tin từ token
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
                    //lưu token vào redis
                    authRedisService.saveNewAccessTokenToServer(authRedisDTO);
                    responseData.put(Constant.RESPONSE_KEY.DATA, newAccessToken);
                } else {
                    result = new Result(
                            ResponseCode.TOKEN_EXPIRATION.getCode(),
                            false,
                            ResponseCode.TOKEN_EXPIRATION.getMessage()
                    );
                    responseData.put(Constant.RESPONSE_KEY.RESULT, result);
                    return responseData;
                }
            } else {
                result = new Result(
                        ResponseCode.REFRESH_TOKEN_MISSING.getCode(),
                        false,
                        ResponseCode.REFRESH_TOKEN_MISSING.getMessage()
                );
                responseData.put(Constant.RESPONSE_KEY.RESULT, result);
                return responseData;
            }
        } catch (Exception e) {
            log.error(Constant.MESSAGE_LOG, transactionId, "Xảy ra ngoại lệ khi thực hiện tạo mới access token", e.getMessage());
            result = Result.SYSTEM_ERR();
        }
        responseData.put(Constant.RESPONSE_KEY.RESULT, result);
        return responseData;
    }

    public Map<String, Object> signInWithGoogle(String authCode, HttpServletResponse response,String transactionId) {
        Map<String,Object> responseData=new HashMap<>();
        Result result=Result.Ok();
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
        } catch (Exception e) {
            log.error(Constant.MESSAGE_LOG,transactionId,"Xảy ra lỗi khi thực hiện xử lý đăng nhập bằng google",transactionId);
            result=Result.SYSTEM_ERR();
        }
        responseData.put(Constant.RESPONSE_KEY.RESULT,result);
        return responseData;
    }

    public Map<String, Object> getAuthUrl(String type, String transactionId) {
        Map<String, Object> responseData = new HashMap<>();
        Result result = Result.Ok();
        try {
            if (type.equalsIgnoreCase("GOOGLE")) {
                responseData.put(Constant.RESPONSE_KEY.DATA, googleAuth.getAuthUrl());
            } else {
                result = new Result(ResponseCode.TYPE_LOGIN_NOT_EXISTS.getCode(), false, ResponseCode.TYPE_LOGIN_NOT_EXISTS.getMessage());
            }
        } catch (Exception e) {
            log.error(Constant.MESSAGE_LOG, transactionId, "Xảy ra lỗi khi lấy url đăng nhập bằng google", e.getMessage());
            result = Result.SYSTEM_ERR();
        }
        responseData.put(Constant.RESPONSE_KEY.RESULT, result);
        return responseData;
    }


    public Map<String, Object> sendMailResetPassword(String email, String transactionId) {
        Map<String, Object> responseData = new HashMap<>();
        Result result = Result.Ok();
        try {
            if (!StringUtils.hasText(email)) {
                result = new Result(ResponseCode.REQUEST_DATA_MISSING.getCode(), false, ResponseCode.REQUEST_DATA_MISSING.getMessage());
                responseData.put(Constant.RESPONSE_KEY.RESULT, result);
                return responseData;
            }
            String token = TokenGenerator.generateToken(150);
            mailPublish.pushEventToQueue(email, token);
            responseData.put(Constant.RESPONSE_KEY.DATA, "Password reset email sent successfully!!");
        } catch (Exception e) {
            log.error(Constant.MESSAGE_LOG, transactionId, "Xảy ra ngoại lệ khi gửi mail reset mật khẩu", e.getMessage());
            result = Result.SYSTEM_ERR();
        }
        responseData.put(Constant.RESPONSE_KEY.RESULT, result);
        return responseData;
    }

    @Transactional
    public Map<String, Object> resetPassword(ResetPasswordDTO resetPasswordDTO, BindingResult bindingResult, String transactionId) {
        Map<String, Object> responseData = new HashMap<>();
        Result result = Result.Ok();
        try {
            if (bindingResult.hasErrors()) {
                result = new Result(ResponseCode.REQUEST_DATA_MISSING.getCode(), false, ResponseCode.REQUEST_DATA_MISSING.getMessage());
                responseData.put(Constant.RESPONSE_KEY.RESULT, result);
                return responseData;
            }
            String authorizationToken = redisService.getValueAndRemove(resetPasswordDTO.getEmail());
            if (authorizationToken.equalsIgnoreCase(resetPasswordDTO.getAuthorization())) {
                userRepositoryJPA.updatePasswordByEmail(resetPasswordDTO.getEmail(), passwordEncoder.encode(resetPasswordDTO.getPassword()));
            } else {
                result = new Result(ResponseCode.TOKEN_RESET_PASSWORD_NOT_ALLOW.getCode(), false, ResponseCode.TOKEN_RESET_PASSWORD_NOT_ALLOW.getMessage());
                responseData.put(Constant.RESPONSE_KEY.RESULT, result);
                return responseData;
            }
        } catch (Exception e) {
            log.error(Constant.MESSAGE_LOG, transactionId, "Xảy ra ngoại lệ khi reset mật khẩu", e.getMessage());
            result = Result.SYSTEM_ERR();
        }
        responseData.put(Constant.RESPONSE_KEY.RESULT, result);
        return responseData;
    }

    public Map<String, Object> logout(String refreshToken, String transactionId) {
        Map<String, Object> responseData = new HashMap<>();
        Result result = Result.Ok();
        try {
            if (!StringUtils.hasText(refreshToken)) {
                result = new Result(ResponseCode.REQUEST_DATA_MISSING.getCode(), false, ResponseCode.REQUEST_DATA_MISSING.getMessage());
                responseData.put(Constant.RESPONSE_KEY.RESULT, result);
                return responseData;
            }
            publishLogoutEvent(refreshToken, Constant.AuthEventType.LOGOUT);
        } catch (Exception e) {
            log.error(Constant.MESSAGE_LOG, transactionId, "Xảy ra ngoại lệ khi đăng xuất khỏi thiết bị", e.getMessage());
            result = Result.SYSTEM_ERR();
        }
        responseData.put(Constant.RESPONSE_KEY.RESULT, result);
        return responseData;
    }

    public Map<String, Object> logoutAll(String refreshToken, String transactionId) {
        Map<String, Object> responseData = new HashMap<>();
        Result result = Result.Ok();
        try {
            if (!StringUtils.hasText(refreshToken)) {
                result = new Result(ResponseCode.REQUEST_DATA_MISSING.getCode(), false, ResponseCode.REQUEST_DATA_MISSING.getMessage());
                responseData.put(Constant.RESPONSE_KEY.RESULT, result);
                return responseData;
            }
            publishLogoutEvent(refreshToken, Constant.AuthEventType.LOGOUT_ALL);
        } catch (Exception e) {
            log.error(Constant.MESSAGE_LOG, transactionId, "Xảy ra ngoại lệ khi reset mật khẩu", e.getMessage());
            result = Result.SYSTEM_ERR();
        }
        responseData.put(Constant.RESPONSE_KEY.RESULT, result);
        return responseData;
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

//        try {
//            authPublish.publishEvent(jsonMapper.objectToJson(pubSubMessage));
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
    }

    private AuthResponseData getResponseAuthData(User user, HttpServletResponse response) {
        Collection<GrantedAuthority> listAuthorities = roleRepositoryJPA.
                findRoleByUsername(user.getUserName())
                .stream()
                .map(e -> (GrantedAuthority) e::getRoleName).collect(Collectors.toList());
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
        AuthRedisDTO authRedisDTO = AuthRedisDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .username(user.getUserName())
                .authorities(listAuthorities)
                .build();
        authRedisService.addNewDataToSet(user.getUserName(), authRedisDTO);
        return authResponseData;
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
//        try {
////            authPublish.publishEvent(jsonMapper.objectToJson(pubSubMessage));
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
    }
}
