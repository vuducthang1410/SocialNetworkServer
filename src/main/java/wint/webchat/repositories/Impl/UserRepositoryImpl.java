package wint.webchat.repositories.Impl;

import jakarta.persistence.*;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import wint.webchat.entities.user.User;
import wint.webchat.mapper.MapperObj;
import wint.webchat.modelDTO.reponse.ProfileDTO;
import wint.webchat.repositories.IUserRepository;

import java.sql.Date;
import java.util.List;

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
        entityManager.merge(user);
        return ResponseEntity.ok("Update successfully!!");
    }

    @Override
    public List<Object[]> getList(Long id, int startGetter, int AmountGet) {
        return null;
    }

    @Override
    public List<ProfileDTO> getProfile(long id) {
//        Query query = entityManager.createQuery(
//                "select u.id ,u.fullName,u.email,u.urlAvatar,u.dateOfBirth,u.describe," +
//                        "u.isOnline,u.urlImgCover,u.idAddress,count(f.id)as amountFriend " +
//                        "from User u " +
//                        "left join Friend f " +
//                        "on (u.id=f.userInvitationReceiver.id or u.id=f.userInvitationSender.id) " +
//                        "and f.isAccept=true " +
//                        "where u.id=:userId " +
//                        "group by u.id,u.fullName,u.email,u.describe,u.urlAvatar," +
//                        "u.urlImgCover,u.dateOfBirth,u.idAddress,u.isOnline", ProfileDTO.class);
//        query.setParameter("userId", id);
//        List<ProfileDTO> resultList = query.getResultList();
//        return resultList;
        return null;
    }
}
