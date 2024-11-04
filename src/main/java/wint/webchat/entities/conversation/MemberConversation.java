package wint.webchat.entities.conversation;

import jakarta.persistence.*;
import lombok.CustomLog;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import wint.webchat.entities.BaseEntity;
import wint.webchat.entities.user.User;

import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "tbl_member_conversation")
public class MemberConversation extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID",length = 50)
    private String id;
    @Column(name = "ID_CONVERSATION",length = 50)
    private String conversationId;
    @Column(name = "ID_USER",length = 50)
    private String idUserJoinConversation;
    @Column(name = "NAME",length = 200,columnDefinition = "nvarchar")
    private String name;
    @Column(name = "IS_CREATE_CONVERSATION",length = 1)
    private String isCreateConversation;
    @Column(name = "TIME_JOIN_CONVERSATION")
    private Timestamp timeJoinConversation;
    @Column(name = "TIME_EXIT_CONVERSATION")
    private Timestamp timeExitConversation;
}
