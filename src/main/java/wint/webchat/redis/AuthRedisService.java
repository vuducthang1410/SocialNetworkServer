package wint.webchat.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import wint.webchat.modelDTO.PubSubDTO.AuthRedisDTO;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthRedisService {
    private final RedisTemplate<String, Object> redisTemplate;

    public List<AuthRedisDTO> getDataInSetByKey(String key) {
        SetOperations<String, Object> setOfValue = redisTemplate.opsForSet();
        Set<Object> data = setOfValue.members(key);
        if (data != null) {
            List<AuthRedisDTO> listUser = data.stream()
                    .map(e -> getAuthRedisDTO((LinkedHashMap<String, Object>) e))
                    .toList();
            return listUser;
        }
        return null;
    }

    public void deleteTokenIsExpiration(AuthRedisDTO authRedisDTO) {
        SetOperations<String, Object> setOfValue = redisTemplate.opsForSet();
        setOfValue.remove(authRedisDTO.getUsername(), authRedisDTO);
    }

    public <T> void addNewDataToSet(String key, T t) {
        SetOperations<String, Object> listOfValue = redisTemplate.opsForSet();
        redisTemplate.opsForSet().add(key, t);
    }

    public AuthRedisDTO getAuthRedisDTO(LinkedHashMap<String, Object> data) {
        ArrayList<LinkedHashMap<String, String>> arrayList = (ArrayList<LinkedHashMap<String, String>>) data.get("authorities");
        Collection<GrantedAuthority> authority = arrayList.stream()
                .map(e -> new GrantedAuthority() {
                    @Override
                    public String getAuthority() {
                        return e.get("authority");
                    }
                })
                .collect(Collectors.toList());
        SetOperations<String, Object> listOfValue = redisTemplate.opsForSet();
        return AuthRedisDTO.builder()
                .accessToken((String) data.get("accessToken"))
                .authorities(authority)
                .refreshToken((String) data.get("refreshToken"))
                .username((String) data.get("username"))
                .build();
    }
    public boolean isAccessTokenContainRedis(String token,String key){
        List<AuthRedisDTO> authRedisDTOList=getDataInSetByKey(key);
        return authRedisDTOList.stream().anyMatch(e->e.getAccessToken().equalsIgnoreCase(token));
    }
    public boolean isRefreshTokenContainRedis(String token,String key){
        List<AuthRedisDTO> authRedisDTOList=getDataInSetByKey(key);
        return authRedisDTOList.stream().anyMatch(e->e.getRefreshToken().equalsIgnoreCase(token));
    }
    public Collection<GrantedAuthority> getGrantedAuthoritiesWithRefreshToken(String key){
        SetOperations<String, Object> setOfValue = redisTemplate.opsForSet();
        Set<Object> data = setOfValue.members(key);
        if (data != null) {
            List<AuthRedisDTO> listUser = data.stream()
                    .map(e -> getAuthRedisDTO((LinkedHashMap<String, Object>) e))
                    .toList();
            return listUser.stream().findFirst().get().getAuthorities();
        }
        return null;
    }
}
