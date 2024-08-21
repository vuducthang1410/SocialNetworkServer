package wint.webchat.controller.user;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import wint.webchat.entities.user.User;
//import wint.webchat.security.CustomUserDetail;

@ControllerAdvice
public class GlobalController {
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<String> handlerExpiredJwtException(ExpiredJwtException ex){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token hết hạn");
    }
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Object> handlerMaxUploadSizeExceededException(MaxUploadSizeExceededException me){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File size exceeds the maximum allowed limit.");
    }
}
