package wint.webchat.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccountController {
    @GetMapping({"/login"})
    public String login(){
        return "Login";
    }
}
