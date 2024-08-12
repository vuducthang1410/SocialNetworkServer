package wint.webchat.entities.post;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.checkerframework.checker.units.qual.C;
import wint.webchat.entities.group.Group;

import java.io.Serializable;


@Entity
@Getter
@Setter
@Table(name = "tbl_post_group")
public class PostGroup implements Serializable {
    @Id
    @Column(name = "ID_POST")
    private String idPost;

    @Id
    @Column(name = "IS_GROUP")
    private String idGroup;
}
