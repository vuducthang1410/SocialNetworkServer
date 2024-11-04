package wint.webchat.entities.post;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import wint.webchat.entities.BaseEntity;
import wint.webchat.entities.user.User;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Entity
public class Comment extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID",length = 50)
    private String id;
    @Column(columnDefinition = "nvarchar(2000)")
    private String content;
    @Column(name = "POST_ID_COMMENT",length = 50)
    private String post_id;
    @Column(name = "PARENT_COMMENT_ID",length = 50)
    private String parent_comment_Id;
}
