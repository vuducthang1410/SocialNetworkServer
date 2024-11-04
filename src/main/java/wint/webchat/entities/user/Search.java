package wint.webchat.entities.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;
import wint.webchat.entities.BaseEntity;

import java.io.Serializable;
import java.sql.Timestamp;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Search extends BaseEntity implements Serializable {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.AUTO)
    private String id;
    @Column(name = "content",columnDefinition = "nvarchar(500)")
    private String content;

}
