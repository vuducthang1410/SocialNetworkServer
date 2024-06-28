package wint.webchat.service;

import org.checkerframework.checker.units.qual.A;
import org.springframework.stereotype.Service;
import wint.webchat.modelDTO.reponse.ApiResponse;
import wint.webchat.modelDTO.reponse.FriendDTO;

import java.util.List;
public interface IFriendService {
    ApiResponse<List<FriendDTO>> getListFriendById(Long id, int startGet, int amountGet);
    ApiResponse<List<FriendDTO>> getInvitationsReceivedById(Long id, int start, int amount);
    ApiResponse<List<FriendDTO>> getInvitationsSentById(Long id, int start, int amount);
    ApiResponse<String> sendFriendInvitation(Long senderId, Long receiverId);
    ApiResponse<String> deleteFriendRelationship(Long userId1, Long userId2);
    ApiResponse<String> deleteInvitations( Long senderId,Long receiverId);

    ApiResponse<String> acceptInvitationFriend(Long senderId, Long receiverId);

}
