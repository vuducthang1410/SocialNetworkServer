package wint.webchat.repositories.Impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import wint.webchat.entities.user.Role;
import wint.webchat.entities.user.User;
import wint.webchat.entities.user.UserRole;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class UserRoleRepositoryImpl {
    @PersistenceContext
    private EntityManager entityManager;
    private static final Logger log= LogManager.getLogger(UserRoleRepositoryImpl.class);
    public void addRoleForUser(User user, List<Role> roleList){
        Set<UserRole> userRoleSet=new HashSet<>();
        try {
            entityManager.persist(user);
            for (Role role : roleList) {
                if (role != null && user != null) { // Kiểm tra role và user khác null
                    userRoleSet.add(new UserRole(role.getId(), user.getId()));
                }
            }
            userRoleSet.forEach(e->entityManager.persist(e));
        }catch (Exception e){
            log.error("Server error: xảy ra ngoại lệ khi đăng ký tài khoản mới! Rootcause: {}",e.getMessage());
        }
    }
}
