package wint.webchat.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wint.webchat.repositories.Impl.IUserRepositoryJPA;

@Service
public class UserService {
    private IUserRepositoryJPA repositoryJPA;

    public UserService(@Autowired IUserRepositoryJPA repositoryJPA) {
        this.repositoryJPA = repositoryJPA;
    }
}
