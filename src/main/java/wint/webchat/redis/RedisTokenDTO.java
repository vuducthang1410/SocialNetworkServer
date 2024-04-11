package wint.webchat.redis;

import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.repository.CrudRepository;

@RedisHash
public class RedisTokenDTO {
    private String accessToken;
    private String refreshToken;
    @TimeToLive
    private Long time;
}
