package wint.webchat.service.Impl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wint.webchat.entities.user.Role;
import wint.webchat.entities.user.User;
import wint.webchat.enums.RedisKeys;
import wint.webchat.google.GoogleAuth;
import wint.webchat.modelDTO.AuthLoginDTO;
import wint.webchat.modelDTO.AuthRedisDTO;
import wint.webchat.modelDTO.AuthSignUpDTO;
import wint.webchat.modelDTO.ResponseAuthData;
import wint.webchat.redis.AuthRedis.AuthConsumer;
import wint.webchat.redis.AuthRedis.AuthProducer;
import wint.webchat.repositories.IRoleRepository;
import wint.webchat.repositories.IUserRepositoryJPA;
import wint.webchat.repositories.Impl.UserRoleRepositoryImpl;
import wint.webchat.security.CustomUserDetail;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
    private final JwtServiceImpl jwtService;
    private final PasswordEncoder passwordEncoder;
    private final IUserRepositoryJPA userRepositoryJPA;
    private final AuthenticationManager authenticationManager;
    private final UserRoleRepositoryImpl userRoleRepository;
    private final IRoleRepository roleRepository;
    private final GoogleAuth googleAuth;
    private final AuthConsumer authConsumer;
    private final AuthProducer authProducer;

    public ResponseEntity<Object> signIn(AuthLoginDTO authLoginDTO, HttpServletResponse response) {
        try {
            var userDb = userRepositoryJPA.findUsersByUserName(authLoginDTO.getUsername());
            if (userDb.isPresent()) {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authLoginDTO.getUsername(), authLoginDTO.getPassword()));
                CustomUserDetail userDetail = userDb.map(CustomUserDetail::new).orElseThrow();
                return ResponseEntity.status(HttpStatus.OK).body(getResponseAuthData(userDetail, response));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not found user from username");
        } catch (AuthenticationException ae) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Password isn't correct");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    public ResponseEntity<String> signUp(AuthSignUpDTO authSignUp) {
        Optional<User> userCheck = userRepositoryJPA.findUsersByUserName(authSignUp.getUsername());
        if (userCheck.isEmpty()) {
            User user = new User(authSignUp.getUsername()
                    , passwordEncoder.encode(authSignUp.getPassword())
                    , authSignUp.getFullname()
            );
            List<Role> list = roleRepository.findByRoleName("ROLE_USER").stream().collect(Collectors.toList());
            if (list.isEmpty()) return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error");
            return userRoleRepository.addRoleForUser(user, list);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("username đã tồn tại");
        }
    }

    public ResponseEntity<String> refreshToken(String refreshToken, String accessToken) {
        try {
            if (refreshToken.length() == 0) throw new Exception();
            jwtService.isTokenExpiration(refreshToken);
            String username = jwtService.getUsernameFromToken(refreshToken);
            if (authProducer.isTokenContainInRedis(username, refreshToken, accessToken)) {
                Collection<GrantedAuthority> authorities=authProducer.getAuthorities(username,refreshToken,accessToken);
                String newAccessToken = jwtService.generateAccessToken(new HashMap<>(), username, authorities);
                addMessageToAuthQueue(username, accessToken, refreshToken,authorities);
                return ResponseEntity.ok(newAccessToken);
            } else
                throw new Exception();
        } catch (ExpiredJwtException eje) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("REFRESH_TOKEN_EXPIRATION");
        } catch (UnsupportedJwtException uje) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("REFRESH_TOKEN_UNSUPPORTED");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("REFRESH_TOKEN_ERROR");
        }
    }

    public ResponseEntity<ResponseAuthData> signInWithGoogle(String authCode) {
        try {
            ResponseAuthData responseAuthData = googleAuth.signIn(authCode);
            return ResponseEntity.ok(responseAuthData);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
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

    private ResponseAuthData getResponseAuthData(CustomUserDetail userDetail, HttpServletResponse response) {
        var accessToken = jwtService.generateAccessToken(new HashMap<>(), userDetail.getUsername(), (Collection<GrantedAuthority>) userDetail.getAuthorities());
        var refreshToken = jwtService.generateRefreshToken(userDetail.getUsername());
        Cookie cookie = new Cookie(RedisKeys.REFRESH_TOKEN.getValueRedisKey(), refreshToken);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        System.out.println("refresh token"+refreshToken);
        System.out.println("access token"+accessToken);
        var user = userDetail.getUser();
        addMessageToAuthQueue(user.getUserName(), accessToken, refreshToken, (Collection<GrantedAuthority>) userDetail.getAuthorities());
        ResponseAuthData responseAuthData = ResponseAuthData.builder()
                .userId(user.getId())
                .urlAvatar(user.getUrlAvatar())
                .fullName(user.getFullName())
                .accessToken(accessToken)
                .role((Collection<GrantedAuthority>) userDetail.getAuthorities())
                .message("Login success")
                .build();
        return responseAuthData;
    }

    private void addMessageToAuthQueue(String username,
                                       String accessToken,
                                       String refreshToken,
                                       Collection<GrantedAuthority> authorities) {
        authConsumer.saveTokenMessage(AuthRedisDTO.builder()
                .username(username)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .authorities(authorities)
                .build());
    }
}
