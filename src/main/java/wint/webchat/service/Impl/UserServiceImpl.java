package wint.webchat.service.Impl;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import wint.webchat.common.Constant;
import wint.webchat.entities.user.User;
import wint.webchat.google.IGoogleDriveFile;
import wint.webchat.mapper.JsonMapper;
import wint.webchat.mapper.MapperObj;
import wint.webchat.modelDTO.UserDataRequest;
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
    private final String DOMAIN_AVTAR = "https://lh3.google.com/u/0/d/";
    private final JsonMapper jsonMapper;
    private final Logger logger=LogManager.getLogger(UserServiceImpl.class);


    @Override
    public ResponseEntity<String> add(AuthSignUpDTO authSignUpDTO) {
        return null;
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public ApiResponse<ProfileDTO> getProfile(String id) {
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
    public ApiResponse<String> updateProfile(MultipartFile avatar, String userDataRequestString) {
        try {
            UserDataRequest userDataRequest = jsonMapper.jsonToObject(userDataRequestString, UserDataRequest.class);
            Optional<User> userOptional = userRepositoryJPA.findUsersById(userDataRequest.getUserId());
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                if (avatar != null) {
                    String urlAvatar = DOMAIN_AVTAR.concat(googleDriveFile.uploadFile(avatar, "Root", true));
                    user.setUrlAvatar(urlAvatar);
                }
                if (userDataRequest.getDateOfBirth() != null && !userDataRequest.getDateOfBirth().isEmpty()) {
                    Date date = new Date(userDataRequest.getDateOfBirth());
                    user.setDateOfBirth(new java.sql.Date(date.getTime()));
                }
                user.setEmail(userDataRequest.getEmail());
                user.setDescribe(userDataRequest.getDescriber());
                user.setIdAddress(userDataRequest.getAddress());
                user.setIsComplete(Constant.STATUS.YES);
                userRepository.update(user);
                return ApiResponse.<String>builder()
                        .code(HttpStatus.OK.value())
                        .error(Map.of())
                        .data("")
                        .build();
            }

        } catch (Exception e) {
            logger.error("Lỗi khi thực hiện cập nhật thông tin tài khoản!!! Root Cause: {}",e.getMessage());
        }
        return ApiResponse.<String>builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .error(Map.of("Not found", "Not found user"))
                .data("")
                .build();
    }
}
