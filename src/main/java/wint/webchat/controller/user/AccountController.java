package wint.webchat.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import wint.webchat.modelDTO.reponse.ApiResponse;
import wint.webchat.modelDTO.reponse.ProfileDTO;
import wint.webchat.service.IUserService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AccountController {
    private final IUserService userService;

    @GetMapping({"/get-profile"})
    public ApiResponse<ProfileDTO> getProfile(@RequestParam("id") Long idUser) {
        return userService.getProfile(idUser);
    }
    @PostMapping(value = "/add-new-profile",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CompletableFuture<Object> addNewProfile(@RequestParam(name = "userData") String userData,
                                                    @RequestParam(name = "avatar") MultipartFile avatar){
        return CompletableFuture.completedFuture(userService.updateProfile(avatar,userData));
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestParam("password") String password) {
        return null;
    }
    @GetMapping("/check-token-expiration")
    public boolean checkTokenExpiration(){
        return true;
    }

}
