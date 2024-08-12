package wint.webchat.entities.conversation;

import jakarta.persistence.*;
import lombok.CustomLog;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import wint.webchat.entities.user.User;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "tbl_member_conversation")
public class MemberConversation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "ID_CONVERSATION")
    private String conversation;
    @Column(name = "ID_USER")
    private String idUserJoinConversation;
    @Column(name = "NAME")
    private String name;
    @Column(name = "IS_CREATE_CONVERSATION")
    private Boolean isCreateConversation;
    @Column(name = "TIME_JOIN_CONVERSATION")
    private Timestamp timeJoinConversation;
    @Column(name = "TIME_EXIT_CONVERSATION")
    private Timestamp timeExitConversation;
    @Column(name = "IS_DELETE")
    private Boolean isDelete;
    @Column(name = "TIME_JOIN")
    @UpdateTimestamp
    private Timestamp timeJoin;




}
