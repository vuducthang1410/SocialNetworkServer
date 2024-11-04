package wint.webchat.entities.user;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import wint.webchat.entities.BaseEntity;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Role extends BaseEntity implements Serializable {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    @Column(name = "ID",length = 50,nullable = false)
    private String id;
    @Basic
    @Column(name = "ROLE_NAME", nullable = true, length = 100)
    private String roleName;
}
