package wint.webchat.service;

import org.springframework.http.ResponseEntity;
import wint.webchat.modelDTO.reponse.ApiResponse;
import wint.webchat.modelDTO.reponse.ProfileDTO;
import wint.webchat.modelDTO.request.AuthSignUpDTO;

public interface IUserService{
    ResponseEntity<String> add(AuthSignUpDTO authSignUpDTO);
    ApiResponse<ProfileDTO> getProfile(long id);
}
