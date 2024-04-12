package wint.webchat.redis.AuthRedis;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import wint.webchat.enums.ChannelRedis;
import wint.webchat.modelDTO.AuthRedisDTO;

@Service
@RequiredArgsConstructor
@Async
public class AuthConsumer {

    private final RedisTemplate<String, Object> redisTemplate;
    public void saveTokenMessage(AuthRedisDTO authRedisDTO) {
        redisTemplate.opsForList().rightPush("AUTH_QUEUE",authRedisDTO );
        redisTemplate.convertAndSend(ChannelRedis.AUTH_CHANNEL.getChannelValue(), "SAVE_TOKEN");
    }
}
