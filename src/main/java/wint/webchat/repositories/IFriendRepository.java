package wint.webchat.repositories;

import wint.webchat.entities.user.Friend;
import wint.webchat.modelDTO.reponse.FriendDTO;

import java.util.List;

public interface IFriendRepository extends BaseMethod<Friend> {
    List<FriendDTO> getListFriendById(String id, int startGet, int amountGet);
    List<FriendDTO> getInvitationsReceivedById(String id, int start, int amount);
    List<FriendDTO> getInvitationsSentById(String id, int start, int amount);
    List<Friend> getFriendById(String userId1,String userId2);
}
