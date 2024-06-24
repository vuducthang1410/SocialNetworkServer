package wint.webchat.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import wint.webchat.mapper.MapperObj;
import wint.webchat.modelDTO.reponse.ApiResponse;
import wint.webchat.modelDTO.reponse.ProfileDTO;
import wint.webchat.modelDTO.request.AuthSignUpDTO;
import wint.webchat.repositories.IUserRepository;
import wint.webchat.repositories.IUserRepositoryJPA;
import wint.webchat.service.IUserService;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final IUserRepositoryJPA userRepositoryJPA;
    private final MapperObj mapperObj;


    @Override
    public ResponseEntity<String> add(AuthSignUpDTO authSignUpDTO) {
        return null;
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public ApiResponse<ProfileDTO> getProfile(long id) {
        var listUser = userRepository.getProfile(id);
        if (listUser.isEmpty()) {
            return ApiResponse.<ProfileDTO>builder()
                    .success(true)
                    .message("")
                    .error(Map.of("message", "Can't find user with id=" + id))
                    .code(HttpStatus.NOT_FOUND.value())
                    .build();
        }
        return ApiResponse.<ProfileDTO>builder()
                .success(true)
                .error(Map.of())
                .message("Successfully retrieved profile")
                .code(HttpStatus.OK.value())
                .data(listUser.get(0))
                .build();
    }
}
