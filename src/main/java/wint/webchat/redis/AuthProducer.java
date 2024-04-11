package wint.webchat.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthProducer {
    private final RedisTemplate<String, String> redisTemplate;
    public String receiveMessage() {
        return redisTemplate.opsForList().leftPop("messageQueue");
    }
}
