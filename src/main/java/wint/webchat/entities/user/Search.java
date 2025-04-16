package wint.webchat.entities.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.io.Serializable;
import java.sql.Timestamp;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_search")
public class Search implements Serializable {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    private String id;
    @Column(name = "CREATE_TIME")
    @CreationTimestamp
    private Timestamp timeSearch;
    @Column(name = "content")
    private String content;
//    @ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
//    @JoinColumn(name = "SEARCH_BY")
    @Column(name = "SEARCH_BY")
    private String searchBy;

}
