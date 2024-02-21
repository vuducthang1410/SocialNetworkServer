package wint.webchat.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping({"/", "/trang-chu", "/home"})
    public String homePage() {
        return "/web/newsfeed";
    }

    @GetMapping("/access-denied")
    public String accessDeny() {
        return "Login";
    }
}
