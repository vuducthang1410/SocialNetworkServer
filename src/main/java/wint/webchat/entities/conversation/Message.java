package wint.webchat.entities.conversation;

import jakarta.persistence.*;
import wint.webchat.entities.conversation.type_message.MediaMessage;
import wint.webchat.entities.conversation.type_message.TextMessage;
import wint.webchat.entities.conversation.type_message.VideoMessage;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Entity
public class Message implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "message_seq_generator")
    @SequenceGenerator(name = "message_seq_generator",sequenceName = "message_sequence",allocationSize = 1)
    private Long id;

    @Column
    private Timestamp timeSend;
    @ManyToOne
    @JoinColumn(name = "id_user_send")
    private MemberConversation memberConversation;
    @OneToOne(mappedBy = "messageText",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private TextMessage textMessage;
    @OneToOne(mappedBy = "messageVideoCall",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private VideoMessage videoMessage;


    @OneToMany(mappedBy = "messageMedia",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<MediaMessage> mediaMessageList;
}
