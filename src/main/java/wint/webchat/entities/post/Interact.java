package wint.webchat.entities.post;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import wint.webchat.entities.BaseEntity;

import java.io.Serializable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Interact extends BaseEntity implements Serializable {
    @Id
    @Column(name ="user_id_interact")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String userInteract;
    @Id
    @Column(name ="post_id_interact")
    private String postInteract;
    @Column(name = "TYPE_INTERACT")
    private int typeInteract;
}
