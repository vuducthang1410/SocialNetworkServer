package wint.webchat.entities.group;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import wint.webchat.entities.user.User;

import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_member_group")
public class MemberGroup implements Serializable {
    @Id
    @Column(name = "USER_ID")
    private String userMember;

    @Id
    @Column(name = "GROUP_ID")
    private String groupMember;


}
