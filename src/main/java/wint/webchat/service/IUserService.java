package wint.webchat.service;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import wint.webchat.modelDTO.reponse.ApiResponse;
import wint.webchat.modelDTO.reponse.ProfileDTO;
import wint.webchat.modelDTO.request.AuthSignUpDTO;

import java.util.Date;

public interface IUserService {
    ResponseEntity<String> add(AuthSignUpDTO authSignUpDTO);

    ApiResponse<ProfileDTO> getProfile(long id);

    ApiResponse<String> updateProfile(Long id, MultipartFile avatar, String firstName,String lastName, String address, Date dateOfBirth, String describer, String email);
}
