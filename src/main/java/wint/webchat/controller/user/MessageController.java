package wint.webchat.controller.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import wint.webchat.modelDTO.reponse.ApiResponse;
import wint.webchat.modelDTO.reponse.MessageDTO;
import wint.webchat.modelDTO.request.MediaMessageDTO;
import wint.webchat.modelDTO.request.TextMessageDTO;
import wint.webchat.repositories.Impl.MemberConversationRepository;
import wint.webchat.service.IMessageService;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MessageController {
    private final IMessageService messageService;
    private final MemberConversationRepository memberConversationRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @GetMapping("/api/message/get-list-by-id")
    public ApiResponse<List<MessageDTO>> getListMessageById(
            @RequestParam("conversationId") int conversationId,
            @RequestParam("amount") int amount,
            @RequestParam("start") int start
    ) {
        if (amount < 0 || start < 0) {
            return ApiResponse.<List<MessageDTO>>builder()
                    .error(Map.of("request param", "start and amount must be greater than 0")).build();
        }
        return messageService.getListMessageByConversationId(amount, (long) conversationId, start);
    }

    @MessageMapping("/send-text-message")
    public void sendTextMessageToUser(@Payload TextMessageDTO textMessageDTO) {
//        Long userIdReceiver = memberConversationRepository.getUserIdByIdMemberConversationId(textMessageDTO.getMemberReceiverId());
//        MessageDTO messageDTO = messageService.saveMessage(textMessageDTO);
//
//        simpMessagingTemplate.convertAndSendToUser(userIdReceiver.toString(), "/message", messageDTO);
//        simpMessagingTemplate.convertAndSendToUser(textMessageDTO.getSenderId().toString(), "/message", messageDTO);
    }

    @PostMapping(value = "/send-media-message",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public void sendMediaMessageToUser(
            @RequestParam(value = "mediaFile",required = false) MultipartFile mediaFile,
            @RequestParam("typeMessage") String typeMessage,
            @RequestParam("conversationId") Long conversationId,
            @RequestParam("senderId") Long senderId,
            @RequestParam("memberReceiverId") Long memberReceiverId
    ) {
//        MediaMessageDTO mediaMessageDTO=new MediaMessageDTO(mediaFile,typeMessage,conversationId,senderId,memberReceiverId);
//        Long userIdReceiver = memberConversationRepository.getUserIdByIdMemberConversationId(mediaMessageDTO.getMemberReceiverId());
//        MessageDTO messageDTO = messageService.saveMediaMessage(mediaMessageDTO);
//
//        simpMessagingTemplate.convertAndSendToUser(userIdReceiver.toString(), "/message", messageDTO);
//        simpMessagingTemplate.convertAndSendToUser(mediaMessageDTO.getSenderId().toString(), "/message", messageDTO);
    }
}
