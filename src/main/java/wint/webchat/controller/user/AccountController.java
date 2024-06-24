package wint.webchat.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wint.webchat.modelDTO.reponse.ApiResponse;
import wint.webchat.modelDTO.reponse.ProfileDTO;
import wint.webchat.service.IUserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AccountController {
    private final IUserService userService;
    @GetMapping({"/get-profile"})
    public ApiResponse<ProfileDTO> getProfile(@RequestParam("id")Long idUser){
       return userService.getProfile(idUser);
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
