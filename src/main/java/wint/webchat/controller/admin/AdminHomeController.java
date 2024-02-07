package wint.webchat.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminHomeController {
    @GetMapping({"/","/trang-chu","/home"})
    public String index(){
        return "/admin/HomeAdmin";
    }
}