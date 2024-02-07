package wint.webchat.entities.conversation;

import jakarta.persistence.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Entity
public class Conversation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "")
    private Long id;
    @Column(name = "name_conversation")
    private String name;
    @Column(name = "time_create")
    private Timestamp timeCreate;

    @OneToMany(mappedBy = "conversation")
    private Set<MemberConversation> listMemberConversation;

}
