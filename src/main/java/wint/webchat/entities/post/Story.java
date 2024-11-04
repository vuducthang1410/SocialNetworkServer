package wint.webchat.entities.post;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import wint.webchat.entities.BaseEntity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Story extends BaseEntity implements Serializable {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name="URL_BACKGROUND_STORY",length = 500)
    private String urlBackgroundStory;
    @Column(name="URL_STORY",length = 500)
    private String urlStory;
    @Column(name="ACCESS_RANGE",length = 500)
    private String accessRange;
}
