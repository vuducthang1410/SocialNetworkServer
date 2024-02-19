package wint.webchat.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import wint.webchat.modelDTO.UserDTO;

public interface IUserService{
    ResponseEntity<String> add(UserDTO userDTO);
}
