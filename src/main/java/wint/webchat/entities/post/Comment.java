package wint.webchat.entities.post;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import wint.webchat.entities.user.User;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_comment")
public class Comment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "CREATE_TIME")
    @CreationTimestamp
    private Timestamp createTime;
    @Column()
    private String content;
    @Column(name = "IS_DELETE")
    private Boolean isDelete;
    @Column(name = "CREATE_BY")
    private String createBy;
    @Column(name = "POST_ID_COMMENT")
    private String post_id;
    @Column(name = "PARENT_COMMENT_ID")
    private String parent_comment_Id;
}
