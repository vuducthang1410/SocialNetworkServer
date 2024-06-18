package wint.webchat.event.mailEvent;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import wint.webchat.common.MailEventType;
import wint.webchat.mapper.JsonMapper;
import wint.webchat.modelDTO.PubSubDTO.MailResetPasswordDTO;


@Service
@RequiredArgsConstructor
@Async
public class MailPublish {
    private final RedisTemplate<String, Object> redisTemplate;
    private final JsonMapper jsonMapper;
private final MailSubscriber mailSubscriber;
    public void pushEventToQueue(String email, String token) {
        try {
            MailResetPasswordDTO mailResetPassword = MailResetPasswordDTO.builder().email(email).token(token).build();
            redisTemplate.opsForList().leftPush(MailEventType.SEND_MAIL_RESET_PASSWORD.getGetMailEventType(), jsonMapper.objectToJson(mailResetPassword));
            mailSubscriber.sendEmail();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
