package wint.webchat.entities.user;

import jakarta.persistence.*;
import lombok.*;
import wint.webchat.entities.BaseEntity;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserRole extends BaseEntity implements Serializable {
    @Id
    @Column(name = "ROLE_ID")
    private String roleId;
    @Id
    @Column(name = "USER_ID")
    private String userId;
}
