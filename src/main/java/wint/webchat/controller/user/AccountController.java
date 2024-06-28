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


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AccountController {
    private final IUserService userService;

    @GetMapping({"/get-profile"})
    public ApiResponse<ProfileDTO> getProfile(@RequestParam("id") Long idUser) {
        return userService.getProfile(idUser);
    }

    @PostMapping(value = "/update-profile", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ApiResponse<String> updateProfile(
            @RequestParam("id") String id,
            @RequestParam(value = "avatar", required = false) MultipartFile avatar,
            @RequestParam("fullName") String fullName,
            @RequestParam("address") String address,
            @RequestParam(value = "dateOfBirth", required = false) String dateOfBirthString,
            @RequestParam("describe") String describe,
            @RequestParam("email") String email
    ) {
        Long userId = Long.parseLong(id);
        Date dateOfBirth = null;
        if (dateOfBirthString != null) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-dd-MM");
                dateOfBirth = dateFormat.parse(dateOfBirthString);
            } catch (ParseException e) {
                return ApiResponse.<String>builder()
                        .message("format date ís yyyy-dd-MM")
                        .code(HttpStatus.BAD_REQUEST.value())
                        .error(Map.of("date format", "required date format yyyy-dd-MM"))
                        .success(false)
                        .build();
            }
        }
        return userService.updateProfile(userId, avatar, fullName, address, dateOfBirth, describe, email);

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
