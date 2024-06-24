package wint.webchat.modelDTO.reponse;

import lombok.*;

import java.sql.Date;

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
    private String idAddress;
    private Long amountFriend;
}
