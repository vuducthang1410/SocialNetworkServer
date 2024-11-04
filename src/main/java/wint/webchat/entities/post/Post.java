package wint.webchat.entities.post;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import wint.webchat.entities.BaseEntity;
import wint.webchat.entities.user.User;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@Setter
@NoArgsConstructor
public class Post extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID_POST", nullable = false)
    private String idPost;
    @Basic
    @Column(name="ACCESS_RANGE",length = 500)
    private String accessRange;
    @Basic
    @Column(name = "CAPTION", length = 20000,columnDefinition = "nvarchar")
    private String caption;
    @Basic
    @Column(name = "type_post_id", length = 50)
    private String typePostId;;
}
