package wint.webchat.entities.conversation;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import wint.webchat.entities.BaseEntity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Conversation extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID",length = 50)
    private String id;
    @Column(name = "NAME_CONVERSATION",length = 200,columnDefinition = "nvarchar")
    private String name;
    @Column(name = "URL_IMAGE",length = 200)
    private String urlImage;

}
