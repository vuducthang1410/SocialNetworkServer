package wint.webchat.controller.user;

import lombok.Getter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping({"/","/trang-chu","/home"})
    public String homePage(Authentication authentication){
        UserDetails userDetails= (UserDetails) authentication.getPrincipal();
        return "/web/newsfeed";
    }
    @GetMapping("/access-denied")
    public String accessDeny(Authentication authentication){
        UserDetails userDetails= (UserDetails) authentication.getPrincipal();
        return "/web/newsfeed";
    }
}
