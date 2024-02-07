package wint.webchat.entities.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
public class Friend implements Serializable {
    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id_sender",foreignKey = @ForeignKey(name = "user_sender_invitation_FK")
    )
    private User userInvitationSender;
    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id_receiver",foreignKey = @ForeignKey(name = "user_receiver_invitation_FK"))
    private User userInvitationReceiver;
    @Column
    private Boolean isAccept;
    @Column
    private Timestamp timeSend;
    @Column
    private Boolean isDelete;
    @Column
    private Boolean isRefuse;
}
