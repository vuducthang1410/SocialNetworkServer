package wint.webchat.controller.user;

import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import wint.webchat.entities.user.User;
import wint.webchat.security.CustomUserDetail;

@ControllerAdvice
public class GlobalController {
    @ModelAttribute("userData")
    public User homePage(Authentication authentication, Model model){
        if(authentication!=null){
        CustomUserDetail userDetails= (CustomUserDetail) authentication.getPrincipal();
        model.addAttribute("userData",userDetails.getUser());
        return userDetails.getUser();}
        return null;
    }
}
