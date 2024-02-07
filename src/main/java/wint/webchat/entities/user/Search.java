package wint.webchat.entities.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Search implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "search_seq")
    @SequenceGenerator(name = "search_seq",sequenceName = "search_sequence")
    private Long id;
    @Column
    private Timestamp timeSearch;
    @Column(name = "content",columnDefinition = "nvarchar(500)")
    private String content;
    @ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id_search")
    private User userSearch;
}
