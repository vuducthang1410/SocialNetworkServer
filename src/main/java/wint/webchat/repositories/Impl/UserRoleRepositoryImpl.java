package wint.webchat.repositories.Impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;
import wint.webchat.entities.user.Role;
import wint.webchat.entities.user.User;
import wint.webchat.entities.user.UserRole;

import java.util.List;
@Repository
public class UserRoleRepositoryImpl {
    @PersistenceContext
    private EntityManager entityManager;
    @Transactional
    public ResponseEntity<String> addRoleForUser(User user, List<Role> roleList){
        try {
            for (Role role : roleList) {
                if (role != null && user != null) { // Kiểm tra role và user khác null
                    entityManager.persist(user);
                    entityManager.persist(new UserRole(role, user));
                }
            }
            entityManager.flush();
            return ResponseEntity.status(HttpStatus.CREATED).body("register success");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
