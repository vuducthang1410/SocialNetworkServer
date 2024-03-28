package wint.webchat.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wint.webchat.modelDTO.PostDTO;
import wint.webchat.service.IPostService;

import java.util.List;

@RestController
public class PostController {
    private final IPostService postService;

    public PostController(@Autowired IPostService postService) {
        this.postService = postService;
    }

    @GetMapping("/get-list-post-profile")
    public List<PostDTO> getListPostInProfile(@RequestParam(name = "id",defaultValue = "1000")Long id,
                                              @RequestParam(name = "startGetter",defaultValue = "1")Integer startGetter,
                                              @RequestParam(name = "amountGetter",defaultValue = "1")Integer amountGetter){
        return postService.getListPost(id,startGetter,amountGetter);
    }
}
