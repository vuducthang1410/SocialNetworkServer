package wint.webchat.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class RedisRepository {
    private final RedisTemplate<String,Object> redisTemplate;
    public void setAccessToken(String id, Map<String,String> value){
//        redisTemplate.opsForValue().set();
//        redisTemplate.opsForList().set(id,value,);
    }
}
