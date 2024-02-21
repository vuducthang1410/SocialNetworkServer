package wint.webchat.repositories.Impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import wint.webchat.entities.user.Role;
import wint.webchat.entities.user.User;
import wint.webchat.entities.user.UserRole;
import wint.webchat.repositories.IUserRepository;

import java.util.List;
import java.util.Set;

@Repository
public class UserRepositoryImpl implements IUserRepository {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    @Transactional
    public ResponseEntity<String> add(User user) {
        Role role=new Role("user","user");
        UserRole userRole=new UserRole(role,user);
        entityManager.persist(user);
        entityManager.persist(userRole);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<String> delete(int id) {
        return null;
    }

    @Override
    public ResponseEntity<String> update(User user) {
        return null;
    }

    @Override
    public List<Object> getList(int condition) {
        return null;
    }
}
