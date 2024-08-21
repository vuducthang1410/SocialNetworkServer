//package wint.webchat.service.Impl;
//
//import lombok.AllArgsConstructor;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import wint.webchat.repositories.IUserRepositoryJPA;
//import wint.webchat.security.CustomUserDetail;
//
//@Service
//@AllArgsConstructor
//public class CustomUserDetailServiceImpl implements UserDetailsService {
//    private final IUserRepositoryJPA userRepositoryJPA;
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        var user=userRepositoryJPA.findUsersByUserName(username);
//        return user.map(CustomUserDetail::new)
//                .orElseThrow(()->new UsernameNotFoundException("Username or password isn't correct"));
//    }
//}
