package wint.webchat.service;

import org.springframework.http.ResponseEntity;
import wint.webchat.modelDTO.reponse.ProfileDTO;
import wint.webchat.modelDTO.request.AuthSignUpDTO;

public interface IUserService{
    ResponseEntity<String> add(AuthSignUpDTO authSignUpDTO);
    ProfileDTO getProfile(int id);
}
