package wint.webchat.entities.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "tbl_friend")
public class Friend implements Serializable {
    @Id
    @Column(name = "USER_ID_SENDER")
    private String userIdSender;
    @Id
    @Column(name = "USER_ID_RECEIVER")
    private String userIdReceiver;
    @Column
    private Boolean isAccept;
    @Column(name = "TIME_CREATE")
    @UpdateTimestamp
    private Timestamp timeCreate;
    @Column(name = "IS_DELETE")
    private Boolean isDelete;
    @Column(name = "IS_REFUSE")
    private Boolean isRefuse;
}
