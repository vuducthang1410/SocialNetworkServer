package wint.webchat.repositories.Impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.hibernate.query.NativeQuery;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import wint.webchat.entities.user.Friend;
import wint.webchat.modelDTO.reponse.FriendDTO;
import wint.webchat.repositories.IFriendRepository;

import java.util.List;

@Repository
public class FriendRepositoryImpl implements IFriendRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ResponseEntity<String> add(Friend friend) {
        entityManager.persist(friend);
        return null;
    }

    @Override
    public ResponseEntity<String> delete(int id) {
        return null;
    }

    @Override
    public ResponseEntity<String> update(Friend friend) {
        entityManager.merge(friend);
        return ResponseEntity.ok("success");
    }

    @Override
    public List<Object[]> getList(Long id, int startGetter, int amountGet) {
        return List.of();
    }

    @Override
    public List<Friend> getFriendById(String userId1, String userId2) {
//        String sql = """
//                select f from  Friend f where (f.userInvitationSender.id=:userId1 and f.userInvitationReceiver.id=:userId2)
//                or (f.userInvitationSender.id=:userId2 and f.userInvitationReceiver.id=:userId1)
//                """;
        Query query = entityManager.createQuery("SELECT f from Friend f", Friend.class);
//        query.setParameter("userId1", userId1);
//        query.setParameter("userId2", userId2);
        return query.getResultList();
    }

    @Override
    public List<FriendDTO> getListFriendById(String id, int startGet, int amountGet) {
        NativeQuery<FriendDTO> nativeQuery = (NativeQuery<FriendDTO>) entityManager.createNativeQuery(
                "select\n" +
                        "        u1_0.id,\n" +
                        "        u1_0.url_avatar,\n" +
                        "        u1_0.is_online,\n" +
                        "        u1_0.full_name,\n" +
                        "        (select\n" +
                        "            count_big(f1_0.is_accept) \n" +
                        "        from\n" +
                        "            friend f1_0 \n" +
                        "        where\n" +
                        "            f1_0.is_accept=1 \n" +
                        "            and (\n" +
                        "                f1_0.user_id_sender=u1_0.id \n" +
                        "                or f1_0.user_id_receiver=u1_0.id\n" +
                        "            )) as amountFriend\n" +
                        "    from\n" +
                        "        [user] u1_0 \n" +
                        "    where\n" +
                        "        u1_0.id in (select\n" +
                        "            f2_0.user_id_receiver \n" +
                        "        from\n" +
                        "            friend f2_0 \n" +
                        "        where\n" +
                        "            f2_0.is_accept=1 \n" +
                        "            and f2_0.user_id_sender=:userId) \n" +
                        "    union\n" +
                        "    select\n" +
                        "        u2_0.id,\n" +
                        "        u2_0.url_avatar,\n" +
                        "        u2_0.is_online,\n" +
                        "        u2_0.full_name,\n" +
                        "        (select\n" +
                        "            count_big(f3_0.is_accept) \n" +
                        "        from\n" +
                        "            friend f3_0 \n" +
                        "        where\n" +
                        "            f3_0.is_accept=1 \n" +
                        "            and (\n" +
                        "                f3_0.user_id_sender=u2_0.id \n" +
                        "                or f3_0.user_id_receiver=u2_0.id\n" +
                        "            )) as amountFriend \n" +
                        "    from\n" +
                        "        [user] u2_0 \n" +
                        "    where\n" +
                        "        u2_0.id in (select\n" +
                        "            f4_0.user_id_sender \n" +
                        "        from\n" +
                        "            friend f4_0 \n" +
                        "        where\n" +
                        "            f4_0.user_id_receiver=:userId\n" +
                        "            and f4_0.is_accept=1) \n" +
                        "   ORDER BY 1 \n" +
                        "    offset\n" +
                        "        :start rows \n" +
                        "    fetch\n" +
                        "        first :amount rows only", FriendDTO.class);
        nativeQuery.setParameter("userId", id);
        nativeQuery.setParameter("amount", amountGet);
        nativeQuery.setParameter("start", startGet);
        return nativeQuery.getResultList();
    }

    @Override
    public List<FriendDTO> getInvitationsReceivedById(String id, int start, int amount) {
//        String sql = "select u.id,u.urlAvatar,u.isOnline,u.fullName," +
//                "(select count(f.isAccept) " +
//                "from Friend f " +
//                "where f.isAccept=true " +
//                "and (f.userInvitationSender.id=u.id " +
//                "or f.userInvitationReceiver.id=u.id)) " +
//                "from User u " +
//                "where u.id in " +
//                "(select f.userInvitationSender.id " +
//                "from Friend f " +
//                "where f.userInvitationReceiver.id = :userId " +
//                "and f.isAccept=false and f.isRefuse=false and f.isDelete=false )";
        Query query = entityManager.createQuery("SELECT f FROM Friend f ", FriendDTO.class);
        query.setMaxResults(amount);
        query.setFirstResult(start);
        return query.getResultList();
    }

    @Override
    public List<FriendDTO> getInvitationsSentById(String id, int start, int amount) {
//        String sql =
//                "select u.id,u.urlAvatar,u.isOnline,u.fullName," +
//                        "(select count(*) " +
//                        "from Friend f " +
//                        "where f.isAccept=false and f.isRefuse=false and f.isDelete=false " +
//                        "and (f.userInvitationSender.id=u.id " +
//                        "or f.userInvitationReceiver.id=u.id) )" +
//                        "from User u " +
//                        "where u.id in " +
//                        "(select f.userInvitationReceiver.id " +
//                        "from Friend f where f.isAccept =false and f.isRefuse=false and f.isDelete=false " +
//                        "and f.userInvitationSender.id= :userId )";
        Query query = entityManager.createQuery("SELECT f FROM  Friend f", FriendDTO.class);
//        query.setParameter("userId", id);
        query.setMaxResults(amount);
        query.setFirstResult(start);
        return query.getResultList();
    }

    public List<FriendDTO> getListNoFriend(String id, int startGet, int amountGet) {
//        String sql = """
//                select u.id,u.urlAvatar,u.isOnline,u.fullName,
//                (select count(*) from Friend f where f.isAccept=false and f.isRefuse=false and f.isDelete=false
//                and (f.userInvitationSender.id=u.id or f.userInvitationReceiver.id=u.id) )
//                from User u
//                where u.id not in
//                (select f.userInvitationReceiver.id
//                from Friend f where f.isAccept =false and f.isRefuse=false and f.isDelete=false
//                and f.userInvitationSender.id= :userId )
//                and u.id not in (select f.userInvitationReceiver.id
//                from Friend f where f.isAccept =false and f.isRefuse=false and f.isDelete=false
//                and f.userInvitationSender.id= :userId )
//                """;
        Query query = entityManager.createQuery("SELECT f FROM Friend f", FriendDTO.class);
//        query.setParameter("userId", id);
//        query.setParameter("amount", amountGet);
//        query.setParameter("start", startGet);
        List<FriendDTO> result = query.getResultList();
        return result;
    }
}
