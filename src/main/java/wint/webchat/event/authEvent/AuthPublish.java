package wint.webchat.event.authEvent;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import wint.webchat.common.Constant;

@Service
@RequiredArgsConstructor
@Async
public class AuthPublish {

    private final RedisTemplate<String, Object> redisTemplate;
    public void publishEvent(String data) {
        redisTemplate.convertAndSend(Constant.ChannelRedis.AUTH_CHANNEL.getChannelValue(),data);
    }
}
