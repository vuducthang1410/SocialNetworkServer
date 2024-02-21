package wint.webchat.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import wint.webchat.modelDTO.ProfileDTO;
import wint.webchat.service.IUserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AccountController {
    private final IUserService userService;
    @GetMapping({"/profile"})
    public Object getProfile(Model model, @RequestParam("id")int id,@ModelAttribute("id")int id1){
        ProfileDTO profileDTO=userService.getProfile(id);
        return profileDTO;
    }
    @PostMapping("/update-profile")
    public Object updateProfile(){
        return null;
    }

}
