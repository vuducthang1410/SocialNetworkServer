package wint.webchat.event.mailEvent;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import wint.webchat.common.MailEventType;
import wint.webchat.mapper.JsonMapper;
import wint.webchat.modelDTO.PubSubDTO.MailResetPasswordDTO;
import wint.webchat.redis.RedisService;
import wint.webchat.service.Impl.MailServiceImpl;

import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
public class MailSubscriber {
    private final RedisTemplate<String, Object> redisTemplate;
    private final JsonMapper jsonMapper;
    @Value("${frontend.url-reset-password}")
    private String URL_RESET_PASSWORD;

    private final RedisService redisService;
    private final MailServiceImpl mailService;

    @Async
//    @Scheduled(fixedDelay = 1000)
    public void sendEmail() throws UnsupportedEncodingException, JsonProcessingException {
        String jsonData = (String) redisTemplate.opsForList().rightPop(MailEventType.SEND_MAIL_RESET_PASSWORD.getGetMailEventType());
        if (jsonData != null) {
            MailResetPasswordDTO data=jsonMapper.jsonReidisToObject(jsonData,MailResetPasswordDTO.class);
            String urlResetPassword = URL_RESET_PASSWORD + "?authorization=" + data.getToken()+"&&email="+data.getEmail();
            mailService.sendHtmlMailResetPassword(data.getEmail(), urlResetPassword);
            redisService.setTokenResetPassword(data.getEmail(), data.getToken());
        }
    }
}
