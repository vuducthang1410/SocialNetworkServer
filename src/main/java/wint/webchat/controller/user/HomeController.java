package wint.webchat.controller.user;

import lombok.Getter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping({"/","/trang-chu","/home"})
    public String homePage(){

        return "/web/newsfeed";
    }
}
