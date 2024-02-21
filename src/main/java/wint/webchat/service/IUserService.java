package wint.webchat.service;

import org.springframework.http.ResponseEntity;
import wint.webchat.modelDTO.ProfileDTO;
import wint.webchat.modelDTO.AuthSignUpDTO;

public interface IUserService{
    ResponseEntity<String> add(AuthSignUpDTO authSignUpDTO);
    ProfileDTO getProfile(int id);
}
