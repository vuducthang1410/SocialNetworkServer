package wint.webchat.entities.user;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Role implements Serializable {
    @Id
    @Column(name = "id_role", nullable = false, length = 50)
    private String idRole;
    @Basic
    @Column(name = "roleName", nullable = true, length = 100)
    private String roleName;
    @Basic
    @Column(name = "lever", nullable = true)
    private Integer lever;

    @OneToMany(mappedBy = "roleUser")
    private Set<UserRole> listUserRole;

}
