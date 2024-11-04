//package wint.webchat.repositories.Impl;
//
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import jakarta.persistence.Query;
//import org.hibernate.query.NativeQuery;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Repository;
//import wint.webchat.entities.user.Friend;
//import wint.webchat.modelDTO.reponse.FriendDTO;
//import wint.webchat.repositories.IFriendRepository;
//
//import java.util.List;
//
//@Repository
//public class FriendRepositoryImpl implements IFriendRepository {
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    @Override
//    public ResponseEntity<String> add(Friend friend) {
//        entityManager.persist(friend);
//        return null;
//    }
//
//    @Override
//    public ResponseEntity<String> delete(String id) {
//        return null;
//    }
//
//    @Override
//    public ResponseEntity<String> update(Friend friend) {
//        entityManager.merge(friend);
//        return ResponseEntity.ok("success");
//    }
//
//    public List<Friend> getFriendById(String userId1, String userId2) {
////        String sql = """
////                select f from  Friend f where (f.userInvitationSender.id=:userId1 and f.userInvitationReceiver.id=:userId2)
////                or (f.userInvitationSender.id=:userId2 and f.userInvitationReceiver.id=:userId1)
////                """;
//        Query query = entityManager.createQuery("", Friend.class);
//        query.setParameter("userId1", userId1);
//        query.setParameter("userId2", userId2);
//        return query.getResultList();
//    }
//
//    @Override
//    public List<Object[]> getList(String id, int startGetter, int amountGet) {
//        return null;
//    }
//
//    @Override
//    public List<FriendDTO> getListFriendById(String id, int startGet, int amountGet) {
//        String querySql = """
//                select u1_0.id,
//                       u1_0.url_avatar,
//                       u1_0.is_online,
//                       u1_0.first_name+' '+u1_0.last_name,
//                       (select count_big(f1_0.is_accept)
//                        from friend f1_0
//                        where f1_0.is_accept = 1
//                          and (
//                                    f1_0.user_id_sender = u1_0.id
//                                or f1_0.user_id_receiver = u1_0.id
//                            )) as amountFriend
//                from tbl_user u1_0
//                where +
//                          u1_0.id in (select f2_0.user_id_receiver
//                                      from friend f2_0
//                                      where f2_0.is_accept = 1
//                                        and f2_0.user_id_sender = :userId)
//                union
//                select u2_0.id,
//                       u2_0.url_avatar,
//                       u2_0.is_online,
//                       u2_0.first_name+' '+u2_0.last_name,
//                       (select +
//                                   count_big(f3_0.is_accept)
//                        from friend f3_0
//                        where +
//                                  f3_0.is_accept = 1
//                          and (
//                                    f3_0.user_id_sender = u2_0.id
//                                or f3_0.user_id_receiver = u2_0.id
//                            )) as amountFriend
//                from tbl_user u2_0
//                where u2_0.id in (select f4_0.user_id_sender
//                                  from friend f4_0
//                                  where f4_0.user_id_receiver = :userId
//                                    and f4_0.is_accept = 1)
//                ORDER BY 1
//                offset :start rows fetch
//                    first :amount rows only
//
//                """;
//        NativeQuery<FriendDTO> nativeQuery = (NativeQuery<FriendDTO>) entityManager.createNativeQuery(
//                querySql
//                , FriendDTO.class);
//        nativeQuery.setParameter("userId", id);
//        nativeQuery.setParameter("amount", amountGet);
//        nativeQuery.setParameter("start", startGet);
//        return nativeQuery.getResultList();
//    }
//
//    @Override
//    public List<FriendDTO> getInvitationsReceivedById(String id, int start, int amount) {
////        String sql = "select u.id,u.urlAvatar,u.isOnline,u.fullName," +
////                "(select count(f.isAccept) " +
////                "from Friend f " +
////                "where f.isAccept=true " +
////                "and (f.userInvitationSender.id=u.id " +
////                "or f.userInvitationReceiver.id=u.id)) " +
////                "from User u " +
////                "where u.id in " +
////                "(select f.userInvitationSender.id " +
////                "from Friend f " +
////                "where f.userInvitationReceiver.id = :userId " +
////                "and f.isAccept=false and f.isRefuse=false and f.isDelete=false )";
//        Query query = entityManager.createQuery("", FriendDTO.class);
//        query.setParameter("userId", id);
//        query.setMaxResults(amount);
//        query.setFirstResult(start);
//        return query.getResultList();
//    }
//
//    @Override
//    public List<FriendDTO> getInvitationsSentById(String id, int start, int amount) {
////        String sql =
////                "select u.id,u.urlAvatar,u.isOnline,u.fullName," +
////                        "(select count(*) " +
////                        "from Friend f " +
////                        "where f.isAccept=false and f.isRefuse=false and f.isDelete=false " +
////                        "and (f.userInvitationSender.id=u.id " +
////                        "or f.userInvitationReceiver.id=u.id) )" +
////                        "from User u " +
////                        "where u.id in " +
////                        "(select f.userInvitationReceiver.id " +
////                        "from Friend f where f.isAccept =false and f.isRefuse=false and f.isDelete=false " +
////                        "and f.userInvitationSender.id= :userId )";
//        Query query = entityManager.createQuery("", FriendDTO.class);
//        query.setParameter("userId", id);
//        query.setMaxResults(amount);
//        query.setFirstResult(start);
//        return query.getResultList();
//    }
//
//    public List<FriendDTO> getListNoFriend(Long id, int startGet, int amountGet) {
////        String sql = """
////                select u.id,u.urlAvatar,u.isOnline,u.fullName,
////                (select count(*) from Friend f where f.isAccept=false and f.isRefuse=false and f.isDelete=false
////                and (f.userInvitationSender.id=u.id or f.userInvitationReceiver.id=u.id) )
////                from User u
////                where u.id not in
////                (select f.userInvitationReceiver.id
////                from Friend f where f.isAccept =false and f.isRefuse=false and f.isDelete=false
////                and f.userInvitationSender.id= :userId )
////                and u.id not in (select f.userInvitationReceiver.id
////                from Friend f where f.isAccept =false and f.isRefuse=false and f.isDelete=false
////                and f.userInvitationSender.id= :userId )
////                """;
//        Query query = entityManager.createQuery("", FriendDTO.class);
//        query.setParameter("userId", id);
//        query.setParameter("amount", amountGet);
//        query.setParameter("start", startGet);
//        List<FriendDTO> result = query.getResultList();
//        return result;
//    }
//}
