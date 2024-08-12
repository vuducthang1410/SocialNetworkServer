package wint.webchat.entities.post;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Interact implements Serializable {
    @Id
    @Column(name ="user_id_interact")
    private String userInteract;
    @Id
    @Column(name ="post_id_interact")
    private String postInteract;
    @Column(name = "TYPE_INTERACT")
    private int typeInteract;
}
