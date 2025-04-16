package wint.webchat.entities.post;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import wint.webchat.entities.user.User;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@Setter
@NoArgsConstructor
@Table(name = "tbl_post")
public class Post implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID_POST", nullable = false)
    private String idPost;
    @Basic
    @Column(name="ACCESS_RANGE",length = 500)
    private String accessRange;
    @Basic
    @Column(name = "IS_DELETE", nullable = true)
    private Boolean isDelete;
    @Basic
    @Column(name = "CAPTION")
    private String caption;
    @Basic
    @Column(name = "TIME_CREATE", nullable = true)
    private Timestamp createTime;
    @Basic
    @Column(name = "type_post_id", nullable = true, length = 10)
    private String typePostId;
    @Column(name = "CREATE_BY")
    private String createBy;
}
