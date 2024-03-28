package wint.webchat.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GroupController{

    @GetMapping({"/group"})
    public String index(){
        return "/web/group";
    }
}
