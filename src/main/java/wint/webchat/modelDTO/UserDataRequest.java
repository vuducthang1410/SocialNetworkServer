package wint.webchat.modelDTO;

import lombok.Data;

@Data
public class UserDataRequest {
    private String userId;
    private String sex;
    private String email;
    private String dateOfBirth;
    private String address;
    private String relationshipStatus;
    private String describer;
}
