package wint.webchat.repositories.Impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import wint.webchat.entities.user.User;

import java.util.Optional;

@Repository
public interface IUserRepositoryJPA extends JpaRepository<User,String>{
    @Override
    <S extends User> S saveAndFlush(S entity);
}
