package wint.webchat.entities.post;

import jakarta.persistence.*;
import wint.webchat.entities.user.User;

import java.io.Serializable;

@Entity
public class Interact implements Serializable {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id_interact")
    private User userInteract;
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id_interact")
    private Post postInteract;

    private int typeInteract;
}
