package wint.webchat.modelDTO;

import com.google.api.client.util.Key;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoGoogleResponseDTO {
    @Key("sub")
    private String sub;

    @Key("name")
    private String name;

    @Key("given_name")
    private String givenName;

    @Key("family_name")
    private String familyName;

    @Key("email")
    private String email;

    @Key("picture")
    private String picture;

    @Key("locale")
    private String locale;

    @Key("gender")
    private String gender;

    @Key("birthdate")
    private String birthdate;

}