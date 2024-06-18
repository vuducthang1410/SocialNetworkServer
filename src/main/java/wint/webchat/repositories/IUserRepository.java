package wint.webchat.repositories;

import wint.webchat.entities.user.User;
import wint.webchat.modelDTO.reponse.ProfileDTO;

public interface IUserRepository extends BaseMethod<User> {
    ProfileDTO getProfile(int id);
}
