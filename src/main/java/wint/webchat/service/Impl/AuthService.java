package wint.webchat.service.Impl;

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
import wint.webchat.google.GoogleAuth;
import wint.webchat.modelDTO.AuthLoginDTO;
import wint.webchat.modelDTO.AuthSignUpDTO;
import wint.webchat.modelDTO.ResponseAuthData;
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

    public ResponseEntity<Object> signIn(AuthLoginDTO authLoginDTO) {
        try {
            var userDb = userRepositoryJPA.findUsersByUserName(authLoginDTO.getUsername());
            if (userDb.isPresent()) {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authLoginDTO.getUsername(), authLoginDTO.getPassword()));
                CustomUserDetail userDetail = userDb.map(CustomUserDetail::new).orElseThrow();
                var accessToken = jwtService.generateAccessToken(new HashMap<>(), userDetail);
                var refreshToken = jwtService.generateRefreshToken(userDetail.getUsername());
                ResponseAuthData responseAuthData = ResponseAuthData.builder()
                        .refreshToken(refreshToken)
                        .token(accessToken)
                        .role((Collection<GrantedAuthority>) userDetail.getAuthorities())
                        .message("Login success")
                        .build();
                return ResponseEntity.status(HttpStatus.OK).body(responseAuthData);
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

    public ResponseEntity<String> refreshToken(String accessToken, String refreshToken) {
        return null;
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
}
