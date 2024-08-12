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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class UserRoleRepositoryImpl {
    @PersistenceContext
    private EntityManager entityManager;
    @Transactional
    public ResponseEntity<String> addRoleForUser(User user, List<Role> roleList){
//        Set<UserRole> userRoleSet=new HashSet<>();
//        try {
//            for (Role role : roleList) {
//                if (role != null && user != null) { // Kiểm tra role và user khác null
//                    userRoleSet.add(new UserRole(role, user));
//                }
//            }
//            user.setUserRoleList(userRoleSet);
//            entityManager.persist(user);
//            return ResponseEntity.status(HttpStatus.CREATED).body("register success");
//        }catch (Exception e){
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        }
        return null;
    }
}
