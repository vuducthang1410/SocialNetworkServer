package wint.webchat.service;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import wint.webchat.modelDTO.reponse.ApiResponse;
import wint.webchat.modelDTO.reponse.FriendDTO;

import java.util.List;
public interface IFriendService {
    ApiResponse<List<FriendDTO>> getListFriendById(String id, int startGet, int amountGet);
    ApiResponse<List<FriendDTO>> getInvitationsReceivedById(String id, int start, int amount);
    ApiResponse<List<FriendDTO>> getInvitationsSentById(String id, int start, int amount);

    //          accept	refuse	delete
//    friend	true	false	false
//    sender	false	false	false
//    refuse	false	true	false
//    delete 	false	false	true
    @Modifying
    @Transactional
    ApiResponse<String> sendFriendInvitation(String senderId, String receiverId);

    ApiResponse<String> deleteFriendRelationship(String userId1, String userId2);
    ApiResponse<String> deleteInvitations( String senderId,String receiverId);

    ApiResponse<String> acceptInvitationFriend(String senderId, String receiverId);

}
