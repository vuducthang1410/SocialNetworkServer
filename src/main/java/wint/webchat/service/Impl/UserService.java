package wint.webchat.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import wint.webchat.entities.user.User;
import wint.webchat.modelDTO.UserDTO;
import wint.webchat.repositories.IUserRepository;
import wint.webchat.repositories.Impl.IUserRepositoryJPA;
import wint.webchat.service.IUserService;

@Service
public class UserService implements IUserService {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(@Autowired IUserRepository repository,@Autowired PasswordEncoder passwordEncoder) {
        this.userRepository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ResponseEntity<String> add(UserDTO userDTO) {
        User user=new User(userDTO.getUsername(),passwordEncoder.encode(userDTO.getPassword()),userDTO.getEmail());
        return userRepository.add(user);
    }
}
