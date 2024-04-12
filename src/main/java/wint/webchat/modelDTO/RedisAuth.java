package wint.webchat.modelDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RedisAuth {
    private String accessToken;
    private Collection<GrantedAuthority> authorities;
}
