package wint.webchat.modelDTO.reponse;

import lombok.*;
import org.hibernate.Internal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@ToString
public class FriendDTO {
    private Long id;
    private String urlAvatar;
    private Boolean isOnline;
    private String fullName;
    private Long amountFriend;
}
