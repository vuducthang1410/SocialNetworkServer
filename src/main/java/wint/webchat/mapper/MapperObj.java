package wint.webchat.mapper;

import org.springframework.stereotype.Component;
import wint.webchat.modelDTO.PostDTO;
import wint.webchat.modelDTO.ProfileDTO;

import java.util.Date;
@Component
public class MapperObj {
    public ProfileDTO profileDTO(Object[] obj){
        ProfileDTO profile=new ProfileDTO();
        profile.setId((Long) obj[0]);
        profile.setFullName((String) obj[1]);
        profile.setEmail((String) obj[2]);
        profile.setUrlAvatar((String) obj[3]);
        profile.setDateOfBirth((Date) obj[4]);
        profile.setDescribe((String) obj[5]);
        profile.setIsOnline((Boolean) obj[6]);
        profile.setUrlImgCover((String) obj[7]);
        profile.setAmountFriend((Integer) obj[8]);
        profile.setAmountPost((Integer) obj[9]);
        return profile;
    }
    public PostDTO getPostDTO(Object[] obj){
        PostDTO postDTO=new PostDTO();
        return postDTO;
    }
}
