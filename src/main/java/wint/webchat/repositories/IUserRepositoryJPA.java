package wint.webchat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import wint.webchat.entities.user.User;
import wint.webchat.modelDTO.reponse.ProfileDTO;

import java.util.Optional;

@Repository
@Transactional
public interface IUserRepositoryJPA extends JpaRepository<User, String> {
    Optional<User> findByUserName(String username);

    @Modifying
    @Query("update User u set u.passwordEncrypt = ?2 where u.email=?1")
    void updatePasswordByEmail(@Param("email") String email, @Param("password") String password);

    @Query("""
            SELECT u FROM  User u WHERE u.id= :id
            """)
    Optional<User> findUsersById(@Param("id") String id);
}
