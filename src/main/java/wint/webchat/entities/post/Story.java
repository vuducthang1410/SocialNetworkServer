package wint.webchat.entities.post;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "tbl_story")
public class Story implements Serializable {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name="URL_BACKGROUND_STORY",length = 500)
    private String urlBackgroundStory;
    @Column(name="URL_STORY",length = 500)
    private String urlStory;
    @Column(name="IS_DELETE")
    private Boolean isDelete;
    @CreationTimestamp
    @Column(name="CREATE_TIME")
    private Timestamp createTime;
    @Column(name="CREATE_BY")
    private String createBy;
    @Column(name="ACCESS_RANGE",length = 500)
    private String accessRange;
}
