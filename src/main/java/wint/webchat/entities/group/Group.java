package wint.webchat.entities.group;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import wint.webchat.entities.post.PostGroup;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "[Group]")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name_group",columnDefinition = "nvarchar(255)")
    private String nameGroup;
    @Column(name = "time_create_group")
    private Timestamp timeCreateGroup;
    @OneToMany(mappedBy = "idGroup")
    private List<PostGroup> postGroupList;

    @OneToMany(mappedBy = "groupMember")
    private List<MemberGroup> memberGroupList;
}
