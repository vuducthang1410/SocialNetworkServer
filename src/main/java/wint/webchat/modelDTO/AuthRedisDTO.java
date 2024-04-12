package wint.webchat.modelDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@Setter
@Builder
public class AuthRedisDTO{
    private String username;
    private String refreshToken;
    private String accessToken;
    private Collection<GrantedAuthority> authorities;
}
