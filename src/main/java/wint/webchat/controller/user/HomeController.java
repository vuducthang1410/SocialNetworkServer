package wint.webchat.controller.user;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping({"/","/trang-chu","/home"})
    public String homePage(Authentication authentication, Model model){
        UserDetails userDetails= (UserDetails) authentication.getPrincipal();
        model.addAttribute("user",userDetails);
        return "/web/newsfeed";
    }
    @GetMapping("/access-denied")
    public String accessDeny(Authentication authentication){
        return "Login";
    }

}
