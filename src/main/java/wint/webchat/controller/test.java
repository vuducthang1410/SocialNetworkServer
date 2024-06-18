package wint.webchat.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import wint.webchat.event.authEvent.AuthPublish;
import wint.webchat.modelDTO.request.AuthLoginDTO;
import wint.webchat.modelDTO.PubSubDTO.PubSubMessage;
import wint.webchat.redis.RedisService;
import wint.webchat.service.Impl.MailServiceImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class test {
    private SimpMessagingTemplate messagingTemplate;
    private final RedisService redisService;
    private final MailServiceImpl mailService;
    private final AuthPublish authPublish;

    //    public void setValue(String key, String value) {
//        redisTemplate.opsForValue().set(key, value);
//    }
    @MessageMapping("/test")
    public void testSocket(@Payload String data){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        System.out.println("Received message: " + data);
//        System.out.println("User Authorities: " + authentication.getAuthorities());
//        mailService.sendHtmlMail("123xxthang@gmail.com","Quên mật khẩu","");
    }
    @GetMapping("/test/redis")
    public void getValue() {
//        System.out.println("da get");
//        return redisService.getToken("1001", RedisKeys.ACCESS_TOKEN.getValueRedisKey());
//        mailService.sendHtmlMail("123xxthang@gmail.com","Quên mật khẩu","");
    }
    @GetMapping("/test/send-error")
    public ResponseEntity<String> response() throws JsonProcessingException {
        PubSubMessage<AuthLoginDTO> message=new PubSubMessage<>(UUID.randomUUID().toString(),
                new Date().getTime(),
                new AuthLoginDTO("vuducthang","12345"),
                "AUTH",
                new HashMap<>());
        ObjectMapper mapper=new ObjectMapper();
        String data=mapper.writeValueAsString(message);
        System.out.println(data);
        PubSubMessage<AuthLoginDTO> newData=mapper.readValue(data,PubSubMessage.class);
        System.out.println(newData);
//        authPublish.saveTokenMessage(data);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("token het han roi");
    }
}
