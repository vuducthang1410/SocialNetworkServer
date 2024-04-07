package wint.webchat.entities.user;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserRole implements Serializable {
    @Id
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "role_id")
    private Role roleUser;
    @Id
    @ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User userRole;
}
