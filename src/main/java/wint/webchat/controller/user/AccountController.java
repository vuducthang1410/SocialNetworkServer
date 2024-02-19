package wint.webchat.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import wint.webchat.modelDTO.UserDTO;
import wint.webchat.service.IUserService;

@Controller
public class AccountController {
    private IUserService userService;

    public AccountController(@Autowired IUserService userService) {
        this.userService = userService;
    }

    @GetMapping({"/login"})
    public String login(){
        return "Login";
    }
    @GetMapping("/register")
    public String signIn(){
        return "Login";
    }
    @PostMapping("/register")
    public String register(@ModelAttribute("user")UserDTO userDTO){
        userService.add(userDTO);
        return "redirect:/web/newsfeed";
    }
}
