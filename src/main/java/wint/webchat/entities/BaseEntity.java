package wint.webchat.entities;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.checkerframework.checker.units.qual.C;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract  class BaseEntity {
    @Column(name = "CREATED_TIME")
    @CreationTimestamp
    private Timestamp createdTime;
    @Column(name = "UPDATED_TIME")
    @UpdateTimestamp
    private Timestamp updatedTime;
    @Column(name = "CREATED_BY",length = 50)
    private String createdBy;
    @Column(name = "UPDATED_BY",length = 50)
    private String updatedBy;
    @Column(name ="IS_DELETED",length = 1)
    private String isDeleted;
}
