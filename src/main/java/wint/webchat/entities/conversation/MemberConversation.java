package wint.webchat.entities.conversation;

import jakarta.persistence.*;
import lombok.CustomLog;
import lombok.Getter;
import lombok.Setter;
import wint.webchat.entities.user.User;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
public class MemberConversation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "member_conversation_generator")
    @SequenceGenerator(name = "member_conversation_generator",sequenceName = "member_conversation_seq")
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_conversation")
    private Conversation conversation;

    @ManyToOne
    @JoinColumn
    private User memberConversation;

    @Column(name = "is_create_conversation")
    private Boolean isCreateConversation;
    @Column(name = "time_join_conversation")
    private Timestamp timeJoinConversation;
    @Column(name = "time_exit_conversation")
    private Timestamp timeExitConversation;
    private Boolean isDelete;




}
