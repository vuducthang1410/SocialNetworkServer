package wint.webchat.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class RedisService {
    private final RedisTemplate<String, Object> redisTemplate;

    @Async
    public void setToken(String userId, String token, String typeValue) {
        String tokenKey = generateTokenKey(userId, typeValue);
        Map<String, Object> data = new HashMap<>();
        data.put(typeValue, token);
        redisTemplate.opsForValue().set(tokenKey, data);
    }

    public Map<String, Object> getToken(String userId, String typeValue) {
        String key = generateTokenKey(userId, typeValue);
        var data=(Map<String, Object>) redisTemplate.opsForValue().get(key);
        System.out.println(data);
        return (Map<String, Object>) redisTemplate.opsForValue().get(key);
    }

    private String generateTokenKey(String userId, String value) {
        return userId + ":" + value;
    }
    public void setTokenResetPassword(String key,String value){
        redisTemplate.opsForValue().set(key,value);
        redisTemplate.expire(key, Duration.ofHours(3));
    }
    public String getValue(String key) throws NullPointerException{
        String value=Objects.requireNonNull(redisTemplate.opsForValue().get(key)).toString();
        redisTemplate.delete(key);
        return value;
    }
}
