package wint.webchat.entities.group;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import wint.webchat.entities.BaseEntity;
import wint.webchat.entities.user.User;

import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_member_group")
public class MemberGroup extends BaseEntity implements Serializable {
    @Id
    @Column(name = "ID",length = 50)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "USER_ID",length = 50)
    private String userMember;

    @Column(name = "GROUP_ID",length = 50)
    private String groupMember;


}
