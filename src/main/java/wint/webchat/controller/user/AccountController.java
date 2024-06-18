package wint.webchat.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wint.webchat.service.IUserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AccountController {
    private final IUserService userService;
    @GetMapping({"/profile"})
    public Object getProfile(){
//        ProfileDTO profileDTO=userService.getProfile(id);
        return "heheheh";
    }
    @PostMapping("/update-profile")
    public Object updateProfile(){
        return null;
    }
    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestParam("password") String password) {
        return null;
    }

}
