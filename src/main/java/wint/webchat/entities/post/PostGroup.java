package wint.webchat.entities.post;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import wint.webchat.entities.group.Group;


@Entity
@Getter
@Setter
public class PostGroup {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_post",nullable = false
    ,foreignKey = @ForeignKey(name = "post_group_FK"))
    private Post postGroup;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_group", nullable = false,foreignKey =   @ForeignKey(name = "group_post_FK"))
    private Group idGroup;

}
