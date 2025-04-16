package wint.webchat.entities.user;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name ="tbl_user_role")
public class UserRole implements Serializable {
    @Id
    @Column(name = "ROLE_ID")
    private String roleId;
    @Id
    @Column(name = "USER_ID")
    private String userId;
}
