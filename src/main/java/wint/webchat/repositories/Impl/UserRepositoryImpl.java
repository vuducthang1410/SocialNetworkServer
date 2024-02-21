package wint.webchat.repositories.Impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import wint.webchat.entities.user.Role;
import wint.webchat.entities.user.User;
import wint.webchat.entities.user.UserRole;
import wint.webchat.mapper.MapperObj;
import wint.webchat.modelDTO.ProfileDTO;
import wint.webchat.repositories.IUserRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class UserRepositoryImpl implements IUserRepository {
    @PersistenceContext
    private EntityManager entityManager;
    private final MapperObj mapper;

    public UserRepositoryImpl(@Autowired MapperObj mapper) {
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public ResponseEntity<String> add(User user) {
        return null;
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
    public List<Object[]> getList(Long id,int startGetter,int AmountGet) {
        return null;
    }

    @Override
    public ProfileDTO getProfile(int id) {
        StoredProcedureQuery storedProcedureQuery=entityManager.createStoredProcedureQuery("get_profile");
        storedProcedureQuery.registerStoredProcedureParameter("user_id",Integer.class, ParameterMode.IN);
        storedProcedureQuery.setParameter("user_id",id);
        List<Object[]> resultList=storedProcedureQuery.getResultList();
        return resultList.stream().findFirst().map(mapper::profileDTO).orElse(null);
    }
}
