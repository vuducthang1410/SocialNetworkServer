package wint.webchat.event.authEvent;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import wint.webchat.common.RedisKeys;
import wint.webchat.entities.user.UserRole;
import wint.webchat.mapper.JsonMapper;
import wint.webchat.modelDTO.PubSubDTO.AuthRedisDTO;
import wint.webchat.modelDTO.PubSubDTO.PubSubMessage;
import wint.webchat.modelDTO.RedisAuth;
import wint.webchat.redis.AuthRedisService;
import wint.webchat.security.SecurityAuthority;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthSubscriber {
    private final String authQueue = "AUTH_QUEUE";
    private final transient RedisTemplate<String, Object> redisTemplate;
    private final JsonMapper jsonMapper;
    private final AuthRedisService authRedisService;

    @Async
    public void saveTokenToServer(LinkedHashMap<String, Object> data) throws UnsupportedEncodingException, JsonProcessingException {
        SetOperations<String, Object> listOfValue = redisTemplate.opsForSet();
        AuthRedisDTO authRedisDTO = authRedisService.getAuthRedisDTO(data);
        redisTemplate.opsForSet().add(authRedisDTO.getUsername(), authRedisDTO);
    }
    public void saveNewAccessTokenToServer(LinkedHashMap<String, Object> data) throws UnsupportedEncodingException, JsonProcessingException {
        try {
            SetOperations<String, Object> listOfValue = redisTemplate.opsForSet();
            AuthRedisDTO newAuthUserDto = authRedisService.getAuthRedisDTO(data);
            List<AuthRedisDTO> listAuthUserData=authRedisService.getDataInSetByKey(newAuthUserDto.getUsername());
            AuthRedisDTO oldAuthUserDto=listAuthUserData.stream().filter(e->e.getRefreshToken().equalsIgnoreCase(newAuthUserDto.getRefreshToken())).findFirst().get();
            authRedisService.deleteTokenIsExpiration(oldAuthUserDto);
            authRedisService.addNewDataToSet(newAuthUserDto.getUsername(),newAuthUserDto);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Async
    public void deleteAllTokenByUsername(String key) {
        redisTemplate.delete(key);
    }

}
