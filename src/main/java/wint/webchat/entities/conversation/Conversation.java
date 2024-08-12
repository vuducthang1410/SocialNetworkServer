package wint.webchat.entities.conversation;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Conversation implements Serializable {

    @Id
   @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "NAME_CONVERSATION")
    private String name;
    @Column(name = "TIME_CREATE")
    private Timestamp timeCreate;
    @Column(name = "URL_IMAGE")
    private String urlImage;

}
