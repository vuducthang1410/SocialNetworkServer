package wint.webchat.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import wint.webchat.entities.user.Role;
import wint.webchat.entities.user.User;
import wint.webchat.modelDTO.AuthLoginDTO;
import wint.webchat.modelDTO.AuthSignUpDTO;
import wint.webchat.modelDTO.ResponseData;
import wint.webchat.repositories.IRoleRepository;
import wint.webchat.repositories.IUserRepositoryJPA;
import wint.webchat.repositories.Impl.UserRoleRepositoryImpl;
import wint.webchat.security.CustomUserDetail;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtServiceImpl jwtService;
    private final PasswordEncoder passwordEncoder;
    private final IUserRepositoryJPA userRepositoryJPA;
    private final AuthenticationManager authenticationManager;
    private final UserRoleRepositoryImpl userRoleRepository;
    private final IRoleRepository roleRepository;
    public ResponseData signIn(AuthLoginDTO authLoginDTO){
        ResponseData responseData=new ResponseData();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authLoginDTO.getUsername(), authLoginDTO.getPassword()));
            CustomUserDetail user=userRepositoryJPA.findUsersByUserName(authLoginDTO.getUsername()).map(CustomUserDetail::new).orElse(null);
            assert user != null;
            System.out.println(user.getUser());
            var jwt=jwtService.generateAccessToken(new HashMap<String, Object>(), user);
            System.out.println(jwt);
        }catch (Exception e){
            e.printStackTrace();
        }
        return responseData;
    }

    public ResponseEntity<String> signUp(AuthSignUpDTO authSignUp){
        Optional<User> userCheck=userRepositoryJPA.findUsersByUserName(authSignUp.getUsername());
        if(userCheck.isEmpty()){
            User user=new User(authSignUp.getUsername()
                    ,passwordEncoder.encode(authSignUp.getPassword())
                    , authSignUp.getFullname()
                    ,authSignUp.getEmail()
            );
            List<Role> list=roleRepository.findByRoleName("ROLE_USER").stream().collect(Collectors.toList());
            return userRoleRepository.addRoleForUser(user,list);
        }
        else{
            return ResponseEntity.status(HttpStatus.CONFLICT).body("username đã tồn tại");
        }
    }
    public ResponseEntity<String> refreshToken(String accessToken,String refreshToken){
        return null;
    }
}
