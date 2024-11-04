package wint.webchat.entities.conversation;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import wint.webchat.entities.BaseEntity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Message extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID",length = 50)
    private String id;
    @Column(name = "URL",length = 300)
    private String url;
    @Column(name = "CONTENT",columnDefinition = "nvarchar(2000)")
    private String content;
    @Column(name = "TIME_CALL")
    private Double timeCall;
    @Column(name = "TYPE_TIME",length = 50)
    private String typeTime;
    @Column(name = "TYPE_MESSAGE",length = 50)
    private String  typeMessage;
    @Column(name = "IS_READ",length = 1)
    private String isRead;
    @Column(name = "URL_MESSAGE_MEDIA",length = 200)
    private String urlMessageMedia;

}
