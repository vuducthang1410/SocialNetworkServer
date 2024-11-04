package wint.webchat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import wint.webchat.entities.user.Friend;
import wint.webchat.modelDTO.projection.FriendDtoProj;
import wint.webchat.modelDTO.reponse.FriendDTO;

import java.util.List;

public interface IFriendRepository extends JpaRepository<Friend,String> {
    String queryGetListFriendById = """
                select u1_0.id as id,
                       u1_0.url_avatar as urlAvatar,
                       u1_0.is_online as isOnline,
                       (u1_0.first_name+' '+u1_0.last_name) as fullName,
                       (select count_big(f1_0.is_accept)
                        from friend f1_0
                        where f1_0.is_accept = 1
                          and (
                                    f1_0.user_id_sender = u1_0.id
                                or f1_0.user_id_receiver = u1_0.id
                            )) as amountFriend
                from tbl_user u1_0
                where +
                          u1_0.id in (select f2_0.user_id_receiver
                                      from friend f2_0
                                      where f2_0.is_accept = 1
                                        and f2_0.user_id_sender = :userId)
                union
                select u2_0.id as id,
                       u2_0.url_avatar as urlAvatar,
                       u2_0.is_online as isOnline,
                       (u2_0.first_name+' '+u2_0.last_name) as fullName,
                       (select +
                                   count_big(f3_0.is_accept)
                        from friend f3_0
                        where +
                                  f3_0.is_accept = 1
                          and (
                                    f3_0.user_id_sender = u2_0.id
                                or f3_0.user_id_receiver = u2_0.id
                            )) as amountFriend
                from tbl_user u2_0
                where u2_0.id in (select f4_0.user_id_sender
                                  from friend f4_0
                                  where f4_0.user_id_receiver = :userId
                                    and f4_0.is_accept = 1)
                ORDER BY 1
                offset :start rows fetch
                    first :amount rows only
                
                """;

    @Query(value =queryGetListFriendById,nativeQuery = true)
    List<FriendDtoProj> getListFriendById(String userId, int start, int amount);
//    @Query
//    List<FriendDtoProj> getInvitationsReceivedById(String id, int start, int amount);
//    @Query
//    List<FriendDtoProj> getInvitationsSentById(String id, int start, int amount);
//    List<Friend> findFriendByUserIdReceiverAndUserIdSenderAndIsAcceptAndIsDelete(
//            String userId1,String userId2,Boolean isAccept,Boolean isDelete
//    );
}
