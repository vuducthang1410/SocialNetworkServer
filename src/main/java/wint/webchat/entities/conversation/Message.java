package wint.webchat.entities.conversation;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_message")
public class Message implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "CREATE_TIME")
    @CreationTimestamp
    private Timestamp createTime;
    @Column
    private String createBy;
    @Column(name = "URL",length = 300)
    private String url;
    @Column(name = "CONTENT")
    private String content;
    @Column(name = "TIME_CALL")
    private Double timeCall;
    @Column(name = "TYPE_TIME")
    private String typeTime;
    @Column(name = "TYPE_MESSAGE")
    private String  typeMessage;
    @Column(name = "IS_DELETE")
    private Boolean isDelete;
    @Column(name = "IS_READ")
    private Boolean isRead;
    @Column(name = "URL_MESSAGE_MEDIA")
    private String urlMessageMedia;

}
