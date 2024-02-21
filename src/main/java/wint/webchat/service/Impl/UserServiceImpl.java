package wint.webchat.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import wint.webchat.mapper.MapperObj;
import wint.webchat.modelDTO.ProfileDTO;
import wint.webchat.modelDTO.AuthSignUpDTO;
import wint.webchat.repositories.IUserRepository;
import wint.webchat.repositories.IUserRepositoryJPA;
import wint.webchat.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final IUserRepositoryJPA userRepositoryJPA;
    private final MapperObj mapperObj;

    public UserServiceImpl(@Autowired IUserRepository repository, @Autowired PasswordEncoder passwordEncoder,
                           @Autowired IUserRepositoryJPA userRepositoryJPA, @Autowired MapperObj mapperObj
    ) {
        this.userRepository = repository;
        this.passwordEncoder = passwordEncoder;
        this.userRepositoryJPA=userRepositoryJPA;
        this.mapperObj=mapperObj;
    }

    @Override
    public ResponseEntity<String> add(AuthSignUpDTO authSignUpDTO) {
        return null;
    }

    @Override
    public ProfileDTO getProfile(int id) {
        return userRepository.getProfile(id);
    }
}
