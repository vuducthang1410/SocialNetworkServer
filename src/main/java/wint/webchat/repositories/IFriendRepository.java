package wint.webchat.repositories;

import wint.webchat.entities.user.Friend;
import wint.webchat.modelDTO.reponse.FriendDTO;

import java.util.List;

public interface IFriendRepository extends BaseMethod<Friend> {
    List<FriendDTO> getListFriendById(Long id, int startGet, int amountGet);
    List<FriendDTO> getInvitationsReceivedById(Long id, int start, int amount);
    List<FriendDTO> getInvitationsSentById(Long id, int start, int amount);
    List<Friend> getFriendById(Long userId1,Long userId2);
}
