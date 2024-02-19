package wint.webchat.repositories.Impl;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;
import wint.webchat.entities.user.User;

import java.util.Optional;

@Repository
public interface IUserRepositoryJPA extends JpaRepository<User,String>{
    @Query("""
    select u from User u where u.userName = :username
""")
    @Transactional
    Optional<User> findUsersByUserName(String username);
}
