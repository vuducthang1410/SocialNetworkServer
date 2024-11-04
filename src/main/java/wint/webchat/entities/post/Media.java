package wint.webchat.entities.post;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import wint.webchat.entities.BaseEntity;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Media extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID",length = 50)
    private String id;
    @Column(name = "LINK_MEDIA", columnDefinition = "nvarchar(255)")
    private String linkMedia;
    @Column(name = "TYPE_MEDIA",length = 50)
    private String typeMedia;
    @Column(name = "POST_ID",length = 50)
    private String postMedia;
    @Column(name = "COMMENT_ID",length = 50)
    private String commentId;
}
