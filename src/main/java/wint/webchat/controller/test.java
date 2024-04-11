package wint.webchat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import wint.webchat.enums.RedisKeys;
import wint.webchat.redis.RedisService;

@RestController
@RequiredArgsConstructor
public class test {
    private final RedisService redisService;

    //    public void setValue(String key, String value) {
//        redisTemplate.opsForValue().set(key, value);
//    }
    @GetMapping("/test/redis")
    public Object getValue() {
        System.out.println("da get");
        return redisService.getToken("1001", RedisKeys.ACCESS_TOKEN.getValueRedisKey());
    }
    @GetMapping("/test/send-error")
    public ResponseEntity<String> response(){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("token het han roi");
    }
}
