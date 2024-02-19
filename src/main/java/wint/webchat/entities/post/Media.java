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
public class Media implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "link_media",columnDefinition = "nvarchar(max)")
    private String linkMedia;
    @Column
    private int typeMedia;

    @ManyToOne(cascade = {CascadeType.PERSIST},fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post postMedia;

    @ManyToOne (cascade = {CascadeType.PERSIST},fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_media")
    private Comment commentMedia;
}
