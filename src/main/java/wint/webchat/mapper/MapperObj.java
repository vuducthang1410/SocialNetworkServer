package wint.webchat.mapper;

import org.springframework.stereotype.Component;
import wint.webchat.entities.user.User;
import wint.webchat.modelDTO.PostDTO;
import wint.webchat.modelDTO.reponse.AuthResponseData;
import wint.webchat.modelDTO.reponse.ProfileDTO;

import java.util.Date;
@Component
public class MapperObj {
    public PostDTO getPostDTO(Object[] obj){
        PostDTO postDTO=new PostDTO();
        return postDTO;
    }
    public AuthResponseData mapUserToAuthResponseData(User user){
        return AuthResponseData.builder()
                .userId(user.getId())
                .fullName(user.getFullName())
                .urlAvatar(user.getUrlAvatar())
                .build();
    }
}
