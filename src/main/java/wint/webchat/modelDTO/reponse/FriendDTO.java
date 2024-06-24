package wint.webchat.modelDTO.reponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Internal;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FriendDTO {
    private Integer id;
    private String fullName;
    private boolean isFriend;
    private String urlAvatar;
    private Integer amountFriend;
}
