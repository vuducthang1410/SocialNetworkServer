package wint.webchat.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import wint.webchat.entities.user.User;
import wint.webchat.google.IGoogleDriveFile;
import wint.webchat.mapper.MapperObj;
import wint.webchat.modelDTO.reponse.ApiResponse;
import wint.webchat.modelDTO.reponse.ProfileDTO;
import wint.webchat.modelDTO.request.AuthSignUpDTO;
import wint.webchat.repositories.IUserRepository;
import wint.webchat.repositories.IUserRepositoryJPA;
import wint.webchat.service.IUserService;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final IUserRepositoryJPA userRepositoryJPA;
    private final MapperObj mapperObj;
    private final IGoogleDriveFile googleDriveFile;


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
                    .error(Map.of("message", "Can't find user with id=" + id))
                    .code(HttpStatus.NOT_FOUND.value())
                    .build();
        }
        return ApiResponse.<ProfileDTO>builder()
                .error(Map.of())
                .code(HttpStatus.OK.value())
                .data(listUser.get(0))
                .build();
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Modifying
    public ApiResponse<String> updateProfile(Long id, MultipartFile avatar, String fistName,String lastName, String address, Date dateOfBirth, String describe, String email) {
        Optional<User> userOptional = userRepositoryJPA.findUsersById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (avatar != null) {
                String urlAvatar = "https://lh3.google.com/u/0/d/" + googleDriveFile.uploadFile(avatar, "Root", true);
                user.setUrlAvatar(urlAvatar);
            }

            if (dateOfBirth != null) {
                java.sql.Date sqlDate = new java.sql.Date(dateOfBirth.getTime());
                user.setDateOfBirth(sqlDate);
            }
            user.setEmail(email);
            user.setDescribe(describe);
            user.setFirstName(fistName);
            user.setLastName(lastName);
            user.setIdAddress(address);
            userRepository.update(user);
            return ApiResponse.<String>builder()
                    .code(HttpStatus.OK.value())
                    .error(Map.of())
                    .data("")
                    .build();
        }
        return ApiResponse.<String>builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .error(Map.of("Not found", "Not found user"))
                .data("")
                .build();
    }
}
