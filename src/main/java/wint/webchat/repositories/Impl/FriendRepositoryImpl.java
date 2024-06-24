package wint.webchat.repositories.Impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.query.NativeQuery;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import wint.webchat.entities.user.Friend;
import wint.webchat.modelDTO.reponse.FriendDTO;
import wint.webchat.modelDTO.reponse.ProfileDTO;
import wint.webchat.repositories.IFriendRepository;

import java.util.List;

@Repository
public class FriendRepositoryImpl implements IFriendRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ResponseEntity<String> add(Friend friend) {
        return null;
    }

    @Override
    public ResponseEntity<String> delete(int id) {
        return null;
    }

    @Override
    public ResponseEntity<String> update(Friend friend) {
        return null;
    }

    @Override
    public List<Object[]> getList(Long id, int startGetter, int amountGet) {
        return null;
    }

//    public List<FriendDTO> getRandomUserNotFriend(int userId, int count) {
//        entityManager.createQuery("SELECT u from User  u where u.id != :userId order by newID()").setMaxResults(count).setParameter("userId",userId);
//    }
}
