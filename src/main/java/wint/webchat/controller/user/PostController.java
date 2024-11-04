package wint.webchat.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import wint.webchat.modelDTO.PostDTO;
import wint.webchat.service.IPostService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {
    private final IPostService postService;

    public PostController(@Autowired IPostService postService) {
        this.postService = postService;
    }

    @GetMapping("/get-list-post-profile")
    public List<PostDTO> getListPostInProfile(@RequestParam(name = "id",defaultValue = "1000")String id,
                                              @RequestParam(name = "startGetter",defaultValue = "1")Integer startGetter,
                                              @RequestParam(name = "amountGetter",defaultValue = "1")Integer amountGetter){
        return postService.getListPost(id,startGetter,amountGetter);
    }
    @PostMapping(value = "/add-new-post")
    public ResponseEntity<Object> addNewPost(@RequestParam("userId")int userId,
                                             @RequestParam("typeMedia")String type,
                                             @RequestParam(value = "mediaData",required = false)MultipartFile mediaData,
                                             @RequestParam(value = "content",required = false)String content){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok().body(authentication);
    }
}
