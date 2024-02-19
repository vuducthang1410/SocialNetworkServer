package wint.webchat.entities.post;

import jakarta.persistence.*;
import wint.webchat.entities.user.User;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Entity
public class Comment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    @Column
    private Timestamp timeComment;
    @Column(columnDefinition = "nvarchar(max)" )
    private String content;
    @Column
    private Boolean isDelete;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user_comment")
    private User userComment;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_post")
    private Post postComment;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment")
    private Comment parentComment;

    @OneToMany(mappedBy = "commentMedia")
    private List<Media> listMedia;
    @OneToMany(mappedBy = "parentComment")
    private List<Comment> commentChildList;
}
