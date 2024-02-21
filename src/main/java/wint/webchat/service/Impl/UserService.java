package wint.webchat.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import wint.webchat.entities.user.User;
import wint.webchat.modelDTO.UserDTO;
import wint.webchat.repositories.IUserRepository;
import wint.webchat.repositories.Impl.IUserRepositoryJPA;
import wint.webchat.service.IUserService;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserService implements IUserService {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final IUserRepositoryJPA userRepositoryJPA;

    public UserService(@Autowired IUserRepository repository,@Autowired PasswordEncoder passwordEncoder,
                       @Autowired IUserRepositoryJPA userRepositoryJPA) {
        this.userRepository = repository;
        this.passwordEncoder = passwordEncoder;
        this.userRepositoryJPA=userRepositoryJPA;
    }

    @Override
    public ResponseEntity<String> add(UserDTO userDTO) {
        Optional<User> userCheck=userRepositoryJPA.findUsersByUserName(userDTO.getUsername());
        if(userCheck.isEmpty()){
        User user=new User(userDTO.getUsername(),passwordEncoder.encode(userDTO.getPassword()),userDTO.getFullname());
        return userRepository.add(user);
        }else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
    }
}
