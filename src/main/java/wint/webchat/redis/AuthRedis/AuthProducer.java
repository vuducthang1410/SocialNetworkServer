package wint.webchat.redis.AuthRedis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import wint.webchat.enums.RedisKeys;
import wint.webchat.modelDTO.AuthRedisDTO;
import wint.webchat.modelDTO.RedisAuth;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthProducer {
    private final String authQueue = "AUTH_QUEUE";
    private final transient RedisTemplate<String, Object> redisTemplate;

    @Async
    public void saveNewToken(String type) {
        LinkedHashMap<String, Object> mapAuthDTO = (LinkedHashMap<String, Object>) redisTemplate.opsForList().leftPop(authQueue);
        if (mapAuthDTO != null && !mapAuthDTO.isEmpty()) {
            AuthRedisDTO authRedisDTO = AuthRedisDTO.builder()
                    .username(mapAuthDTO.get("username").toString())
                    .refreshToken(mapAuthDTO.get("refreshToken").toString())
                    .accessToken(mapAuthDTO.get("accessToken").toString())
                    .authorities((Collection<GrantedAuthority>) mapAuthDTO.get("authorities")).build();
            var key = generateKey(authRedisDTO.getUsername());
            Map<String, RedisAuth> tokenMap = (Map<String, RedisAuth>) redisTemplate.opsForValue().get(key);
            if (type.equalsIgnoreCase("SAVE_TOKEN")) {
                if (tokenMap == null) {
                    tokenMap = new HashMap<>();
                }
                tokenMap.put(authRedisDTO.getRefreshToken(), RedisAuth.builder()
                        .accessToken(authRedisDTO.getAccessToken())
                        .authorities(authRedisDTO.getAuthorities()).build());
                redisTemplate.opsForValue().set(key, tokenMap);
            } else {
                if (tokenMap == null) {
                    return;
                }
                tokenMap.remove(authRedisDTO.getRefreshToken());
                redisTemplate.opsForValue().set(key, tokenMap);
            }
        }
    }

    //    @Async
//    public void deleteToken(){
//        LinkedHashMap<String, Object> mapAuthDTO = (LinkedHashMap<String,Object>) redisTemplate.opsForList().leftPop(authQueue);
//        if (mapAuthDTO!=null&&!mapAuthDTO.isEmpty()) {
//            AuthRedisDTO authRedisDTO = AuthRedisDTO.builder()
//                    .username(mapAuthDTO.get("username").toString())
//                    .refreshToken(mapAuthDTO.get("refreshToken").toString())
//                    .accessToken(mapAuthDTO.get("accessToken").toString()).build();;
//            var key = generateKey(authRedisDTO.getUsername());
//            Map<String, String> tokenMap = getToken(key);
//            if (tokenMap == null) {
//                return;
//            }
//            tokenMap.remove(authRedisDTO.getRefreshToken());
//            redisTemplate.opsForValue().set(key, tokenMap);
//        }
//    }
    @Async
    public void deleteAllToken() {
        LinkedHashMap<String, RedisAuth> mapAuthDTO = (LinkedHashMap<String, RedisAuth>) redisTemplate.opsForList().leftPop(authQueue);
        redisTemplate.delete(generateKey(mapAuthDTO.get("userId").toString()));
    }

    public Map<String, LinkedHashMap<String,Object>> getTokenByKey(String key) {
        return (Map<String, LinkedHashMap<String, Object>>) redisTemplate.opsForValue().get(key);
    }

    public boolean isTokenContainInRedis(String username, String refreshToken, String accessToken) {
        Map<String, LinkedHashMap<String, Object>> tokenMap = getTokenByKey(generateKey(username));
        System.out.println(tokenMap!=null);
        System.out.println(!tokenMap.isEmpty());
        String accessTokenInTokenMap= (String) tokenMap.get(refreshToken).get("accessToken");
        System.out.println(accessTokenInTokenMap);
        return tokenMap!=null&&!tokenMap.isEmpty() && accessTokenInTokenMap.equalsIgnoreCase(accessToken);
    }
    public Collection<GrantedAuthority> getAuthorities(String username, String refreshToken, String accessToken) {
        Map<String, LinkedHashMap<String, Object>> tokenMap = getTokenByKey(generateKey(username));
        if( tokenMap!=null&&!tokenMap.isEmpty())
            return (Collection<GrantedAuthority>) tokenMap.get(refreshToken).get("authorities");
        else return null;
    }

    private String generateKey(String username) {
        return username + ":" + RedisKeys.TOKEN.getValueRedisKey();
    }
}
