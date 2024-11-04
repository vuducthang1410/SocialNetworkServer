package wint.webchat.entities.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import wint.webchat.entities.BaseEntity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Friend extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "USER_ID_SENSER")
    private String userIdSender;

    @Column(name = "USER_ID_RECEIVER")
    private String userIdReceiver;
    @Column(name = "STATUS")
    private Boolean status;
}
