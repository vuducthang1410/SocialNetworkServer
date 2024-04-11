package wint.webchat.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthConsumer {

    private final RedisTemplate<String, String> redisTemplate;

    public void sendMessage(String message) {
        redisTemplate.opsForList().rightPush("messageQueue", message);
    }
}
