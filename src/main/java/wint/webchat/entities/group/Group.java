package wint.webchat.entities.group;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import wint.webchat.entities.post.PostGroup;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "tbl_group")
public class Group implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "NAME_GROUP",columnDefinition = "nvarchar(255)")
    private String nameGroup;
    @Column(name = "CREATE_TIME")
    @CreationTimestamp
    private Timestamp createTime;
    @Column(name = "IS_DELETE")
    private Boolean isDelete;
    @Column(name = "IS_PUBLIC")
    private Boolean isPublic;
}
