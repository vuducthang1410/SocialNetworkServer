//package wint.webchat.security;
//
//import lombok.AllArgsConstructor;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//import wint.webchat.entities.user.User;
//import wint.webchat.repositories.RoleRepositoryJPA;
//
//import java.util.Collection;
//import java.util.stream.Collectors;
//
//@AllArgsConstructor
//@Component
//public class CustomUserDetail implements UserDetails {
//    private final User user;
//    @Autowired
//    private final RoleRepositoryJPA repositoryJPA;
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return repositoryJPA.findRoleByRoleName(user.get)
//                user.getUserRoleList()
//                .stream()
//                .map(SecurityAuthority::new)
//                .collect(Collectors.toList());
//        return null;
//    }
//
//    @Override
//    public String getPassword() {
//        return user.getPasswordEncrypt();
//    }
//
//    @Override
//    public String getUsername() {
//        return user.getUserName();
//    }
//
//    public String getFullName(){
//        return user.getFirstName()+user.getLastName();
//    }
//    public String getUrlAvatar(){
//        return user.getUrlAvatar();
//    }
//    public User getUser(){
//        return user;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return user.getIsAccountNonLocked();
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//}
