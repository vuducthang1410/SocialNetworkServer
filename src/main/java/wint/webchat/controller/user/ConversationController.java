package wint.webchat.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wint.webchat.modelDTO.reponse.ApiResponse;
import wint.webchat.modelDTO.reponse.ConversationMessageDTO;
import wint.webchat.modelDTO.request.MediaMessageDTO;
import wint.webchat.modelDTO.request.TextMessageDTO;
import wint.webchat.service.IConversationService;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/conversation")
public class ConversationController {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final IConversationService conversationService;
    @GetMapping("/get-list-conversation")
    public ApiResponse<List<ConversationMessageDTO>> getListConversation(
            @RequestParam int userId,
            @RequestParam int start,
            @RequestParam int amount){
        if(start<0 || amount<1){
            return ApiResponse.<List<ConversationMessageDTO>>builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .error(Map.of("request param","start and amount require >=0 "))
                    .build();
        }
        return conversationService.getListConversation((long)userId,start,amount);

    };
    @MessageMapping("/send-file-message")
    public void sendMessageToUser(@Payload MediaMessageDTO mediaMessageDTO){

    }
    @MessageMapping("/send-request-video-call")
    public void sendRequestVideoCall(@Payload String data){

    }
}
