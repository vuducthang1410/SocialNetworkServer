package wint.webchat.entities.group;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import wint.webchat.entities.BaseEntity;
import wint.webchat.entities.post.PostGroup;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "tbl_group")
public class Group extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID",length = 50)
    private String id;
    @Column(name = "NAME_GROUP",columnDefinition = "nvarchar(255)")
    private String nameGroup;
    @Column(name = "IS_PUBLIC",length = 1)
    private String isPublic;
}
