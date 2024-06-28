package wint.webchat.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import wint.webchat.event.authEvent.AuthPublish;
import wint.webchat.modelDTO.reponse.FriendDTO;
import wint.webchat.redis.RedisService;
import wint.webchat.repositories.IFriendRepository;
import wint.webchat.service.Impl.MailServiceImpl;

@RestController
@RequiredArgsConstructor
public class test {
    private SimpMessagingTemplate messagingTemplate;
    private final RedisService redisService;
    private final MailServiceImpl mailService;
    private final AuthPublish authPublish;
    private final IFriendRepository friendRepository;
    private final ObjectMapper objectMapper;
    //    public void setValue(String key, String value) {
//        redisTemplate.opsForValue().set(key, value);
//    }
    @MessageMapping("/test")
    public void testSocket(@Payload String data){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        System.out.println("Received message: " + data);
//        System.out.println("User Authorities: " + authentication.getAuthorities());
//        mailService.sendHtmlMail("123xxthang@gmail.com","Quên mật khẩu","");
        friendRepository.getList(Long.getLong("1"),1,1);
    }
    @GetMapping("/test/friendAPI")
    public void testFriendAPI(HttpServletResponse response) {
//        Cookie cookie=new Cookie("hehe","hehe");
//        response.addCookie(cookie);
//        Object data=redisService.getValue("vietnam_provinces");
//        System.out.println(data);
        friendRepository.getList(Long.getLong("1"),1,1);
    }
    @GetMapping("/test/redis")
    public void getValue(HttpServletResponse response) {
//        Cookie cookie=new Cookie("hehe","hehe");
//        response.addCookie(cookie);
        Object data=redisService.getValue("vietnam_provinces");
        System.out.println(data);
    }
    @GetMapping("/test/send-error")
    public ResponseEntity<String> response() throws JsonProcessingException {
//        PubSubMessage<AuthLoginDTO> message=new PubSubMessage<>(UUID.randomUUID().toString(),
//                new Date().getTime(),
//                new AuthLoginDTO("vuducthang","12345"),
//                "AUTH",
//                new HashMap<>());
        FriendDTO friendDTO=new FriendDTO();
        friendDTO.setId((long) 111);
        friendDTO.setFullName("hehehe");
        friendDTO.setIsOnline(false);
        friendDTO.setUrlAvatar("1111");
//        ObjectMapper mapper=new ObjectMapper();
//        String data=mapper.writeValueAsString(message);
        String data=objectMapper.writeValueAsString(friendDTO);
        System.out.println(data);
//        PubSubMessage<AuthLoginDTO> newData=mapper.readValue(data,PubSubMessage.class);
//        System.out.println(newData);
//        authPublish.saveTokenMessage(data);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("token het han roi");
    }
}
