package wint.webchat.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import wint.webchat.modelDTO.reponse.ApiResponse;
import wint.webchat.modelDTO.reponse.FriendDTO;
import wint.webchat.modelDTO.request.FriendIdDTO;
import wint.webchat.modelDTO.request.FriendRequestDTO;
import wint.webchat.service.IFriendService;

import java.util.List;

@RestController
@RequestMapping("/api/friend")
@RequiredArgsConstructor
public class FriendController {
    private final IFriendService friendService;

    @GetMapping("/get-list-friend")
    public ApiResponse<List<FriendDTO>> getListFriendById(
            @RequestParam int userId,
            @RequestParam int start,
            @RequestParam int amount) {
        return friendService.getListFriendById((long) userId, start, amount);
    }

    @GetMapping("/get-list-invitation-receiver")
    public ApiResponse<List<FriendDTO>> getListInvitationReceiverById(
            @RequestParam int userId,
            @RequestParam int start,
            @RequestParam int amount) {
        return friendService.getInvitationsReceivedById((long) userId, start, amount);
    }

    @GetMapping("/get-list-invitation-sender")
    public ApiResponse<List<FriendDTO>> getListInvitationSenderById(
            @RequestParam int userId,
            @RequestParam int start,
            @RequestParam int amount) {
        return friendService.getInvitationsSentById((long) userId, start, amount);
    }

    @PostMapping("/delete-friend")
    public Object acceptInvitation(@RequestBody FriendIdDTO friendIdDTO) {
        return friendService.deleteFriendRelationship(friendIdDTO.getSenderId(), friendIdDTO.getReceiverId());
    }

    @PostMapping("/delete-invitation")
    public Object deleteInvitation(@RequestBody FriendIdDTO friendIdDTO) {
        return friendService.deleteInvitations(friendIdDTO.getSenderId(), friendIdDTO.getReceiverId());
    }
}
