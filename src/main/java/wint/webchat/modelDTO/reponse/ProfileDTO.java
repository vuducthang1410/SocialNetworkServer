package wint.webchat.modelDTO.reponse;

import lombok.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDTO {
    private Long id;
    private String fullName;
    private String email;
    private String urlAvatar;
    private Date dateOfBirth;
    private String describe;
    private Boolean isOnline;
    private String urlImgCover;
    private int amountPost;
    private int amountFriend;
}
