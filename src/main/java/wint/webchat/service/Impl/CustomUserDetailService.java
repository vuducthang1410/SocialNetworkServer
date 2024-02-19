package wint.webchat.service.Impl;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import wint.webchat.repositories.Impl.IUserRepositoryJPA;
import wint.webchat.security.SecurityUserDetail;

@Service
@AllArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final IUserRepositoryJPA userRepositoryJPA;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user=userRepositoryJPA.findUsersByUserName(username);
        return user.map(SecurityUserDetail::new)
                .orElseThrow(()->new UsernameNotFoundException("Not found user"));
    }
}
