package wint.webchat.controller.user;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import wint.webchat.modelDTO.reponse.ApiResponse;
import wint.webchat.modelDTO.reponse.FriendDTO;
import wint.webchat.modelDTO.request.FriendIdDTO;
import wint.webchat.repositories.Impl.FriendRepositoryImpl;
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
    private final FriendRepositoryImpl friendRepository;
    @PersistenceContext
    private EntityManager entityManager;

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
    public List<FriendDTO> getList(
            @RequestParam int userId,
            @RequestParam int start,
            @RequestParam int amount
    ){
       return friendRepository.getListNoFriend((long)userId,start,amount);
    }
    @GetMapping("/search-user")
    public Object search(@RequestParam("email")String email){
        String sql= """
                select u.id,u.fullName,u.urlAvatar from User u where u.email=:email
                """;
        Query query=entityManager.createQuery(sql);
        query.setParameter("email",email);
        var result=query.getResultList();
        return result.size()==0?null:result.stream().findFirst().get();
    }
    @GetMapping("/get-infor")
    public Object getInfor(@RequestParam("userId")Long userId,@RequestParam("userIdSearch")Long userIdSearch){
        String sql= """
                SELECT u.id,u.urlAvatar,u.fullName,
                 (select case when count(f) > 0 then true else false end from Friend f where
                 (f.userInvitationReceiver.id=:userIdSearch
                 and f.userInvitationSender.id=:userId)
                 or(f.userInvitationSender.id=:userIdSearch
                 and f.userInvitationReceiver.id=:userId)) as isFriend from User u where u.id=:userIdSearch
                """;
        Query query=entityManager.createQuery(sql);
        query.setParameter("userId",userId);
        query.setParameter("userIdSearch",userIdSearch);
        var result=query.getResultList();
        return result.size()==0?null:result.stream().findFirst();
    }
}
