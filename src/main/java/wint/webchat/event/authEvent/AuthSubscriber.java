package wint.webchat.event.authEvent;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import wint.webchat.modelDTO.PubSubDTO.AuthRedisDTO;
import wint.webchat.redis.AuthRedisService;

import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthSubscriber {
    private final transient RedisTemplate<String, Object> redisTemplate;
    private final AuthRedisService authRedisService;

    @Async
    public void saveTokenToServer(LinkedHashMap<String, Object> data) throws UnsupportedEncodingException, JsonProcessingException {
        AuthRedisDTO authRedisDTO = authRedisService.getAuthRedisDTO(data);
        redisTemplate.opsForSet().add(authRedisDTO.getUsername(), authRedisDTO);
    }
    public void saveNewAccessTokenToServer(AuthRedisDTO newAuthUserDto) {
        try {
            List<AuthRedisDTO> listAuthUserData=authRedisService.getDataInSetByKey(newAuthUserDto.getUsername());
            AuthRedisDTO oldAuthUserDto=listAuthUserData.stream().filter(e->e.getRefreshToken().equalsIgnoreCase(newAuthUserDto.getRefreshToken())).findFirst().get();
            authRedisService.deleteToken(oldAuthUserDto);
            authRedisService.addNewDataToSet(newAuthUserDto.getUsername(),newAuthUserDto);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Async
    public void deleteAllTokenByUsername(LinkedHashMap<String, Object> data) {
        AuthRedisDTO authRedis = authRedisService.getAuthRedisDTO(data);
        redisTemplate.delete(authRedis.getUsername());
    }
    @Async
    public void deleteRefreshToken(LinkedHashMap<String, Object> data){
        AuthRedisDTO authRedis = authRedisService.getAuthRedisDTO(data);
        List<AuthRedisDTO> authRedisList=authRedisService.getDataInSetByKey(authRedis.getUsername());
        if(!authRedisList.isEmpty()){
            var authRedisInRedis=authRedisList.stream().filter(e->e.getRefreshToken().equalsIgnoreCase(authRedis.getRefreshToken())).findFirst().get();
            authRedisService.deleteToken(authRedisInRedis);
        }
    }
}
