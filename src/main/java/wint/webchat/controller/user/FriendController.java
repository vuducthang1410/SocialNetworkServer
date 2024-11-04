package wint.webchat.controller.user;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wint.webchat.modelDTO.reponse.ApiResponse;
import wint.webchat.modelDTO.reponse.FriendDTO;
import wint.webchat.modelDTO.request.FriendIdDTO;
import wint.webchat.service.IFriendService;
import wint.webchat.service.IUserService;

import java.util.List;

@RestController
@RequestMapping("/api/friend")
@RequiredArgsConstructor
public class FriendController {
    private final IFriendService friendService;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final IUserService iUserService;
    @PersistenceContext
    private EntityManager entityManager;

//    @GetMapping("/get-list-friend")
//    public CompletableFuture<ResponseData<Map<String, Object>>>  getListFriendById(
//            @RequestParam(name = "userId") String userId,
//            @RequestParam(name = "start",required = false,defaultValue = "0") int start,
//            @RequestParam(name = "amount",required = false,defaultValue = "10") int amount,
//            @NonNull @RequestHeader(Constant.TRANSACTION_ID_KEY) String transactionId) {
//        return friendService.getListFriendById(userId, start, amount,transactionId);
//    }

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
    public Object deleteFriend(@RequestBody FriendIdDTO friendIdDTO) {
        return friendService.deleteFriendRelationship(friendIdDTO.getSenderId(), friendIdDTO.getReceiverId());
    }

    @PostMapping("/delete-invitation")
    public Object deleteInvitation(@RequestBody FriendIdDTO friendIdDTO) {
        return friendService.deleteInvitations(friendIdDTO.getSenderId(), friendIdDTO.getReceiverId());
    }

    @MessageMapping("/send-invitation")
    public void sendInvitation(@Payload FriendIdDTO friendDTO) {
        friendService.sendFriendInvitation(friendDTO.getSenderId(), friendDTO.getReceiverId());
        var profile=iUserService.getProfile(friendDTO.getSenderId()).getData();
        FriendDTO friendDTO1=new FriendDTO();
        friendDTO1.setUrlAvatar(profile.getUrlAvatar());
        friendDTO1.setId(profile.getId());
        friendDTO1.setIsOnline(profile.getIsOnline());
        friendDTO1.setAmountFriend(profile.getAmountFriend());
        friendDTO1.setFullName(profile.getFullName());
        simpMessagingTemplate.convertAndSendToUser(friendDTO.getReceiverId().toString(), "/friend",friendDTO1);
    }

    @MessageMapping("/accept-invitation")
    public void acceptInvitation(@Payload FriendIdDTO friendIdDTO) {
        friendService.deleteFriendRelationship(friendIdDTO.getSenderId(), friendIdDTO.getReceiverId());
        simpMessagingTemplate.convertAndSendToUser(
                friendIdDTO.getReceiverId().toString()
                , "/accept-friend", "Lời mời kết bạn đã được chấp nhận");
    }
//    public List<FriendDTO> getList(
//            @RequestParam int userId,
//            @RequestParam int start,
//            @RequestParam int amount
//    ){
//       return friendRepository.getListNoFriend((long)userId,start,amount);
//    }
//    @GetMapping("/search-user")
//    public Object search(@RequestParam("email")String email){
//
//    }
//    @GetMapping("/get-info")
//    public Object getInfo(@RequestParam("userId")Long userId,@RequestParam("userIdSearch")Long userIdSearch){
//
//    }
}
