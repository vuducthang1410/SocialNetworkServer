package wint.webchat.repositories;

import wint.webchat.entities.user.User;
import wint.webchat.modelDTO.reponse.ProfileDTO;

import java.sql.Date;
import java.util.List;

public interface IUserRepository extends BaseMethod<User> {
    List<ProfileDTO> getProfile(String id);

}
