package wint.webchat.entities.post;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_media")
public class Media implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "LINK_MEDIA")
    private String linkMedia;
    @Column(name = "TYPE_MEDIA")
    private String typeMedia;
    @Column(name = "POST_ID")
    private String postMedia;
    @Column(name = "COMMENT_ID")
    private String commentId;
}
