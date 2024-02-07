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
public class Post implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_post", nullable = false)
    private int idPost;
    @Basic
    @Column(name = "privacy_id", nullable = true, length = 10)
    private String privacyId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userIdCreate")
    private User userCreatePost;
    @Basic
    @Column(name = "is_delete", nullable = true)
    private Boolean isDelete;
    @Basic
    @Column(name = "caption", nullable = true, length = 2147483647,columnDefinition = "nvarchar")
    private String caption;
    @Basic
    @Column(name = "time_create_post", nullable = true)
    private Timestamp timeCreatePost;
    @Basic
    @Column(name = "type_post_id", nullable = true, length = 10)
    private String typePostId;


    @OneToMany(mappedBy = "postGroup")
    private List<PostGroup> postGroupList;
    @OneToMany(mappedBy = "postMedia")
    private List<Media> list;
    @OneToMany(mappedBy = "postComment")
    private List<Comment> listComment;
    @OneToMany(mappedBy = "postInteract")
    private List<Interact> listUserInteract;
}
