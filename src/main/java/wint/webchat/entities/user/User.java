package wint.webchat.entities.user;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;
import wint.webchat.common.Constant;
import wint.webchat.entities.BaseEntity;

import java.io.Serializable;
import java.sql.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_user")
public class User extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "USER_NAME", nullable = false, length = 50)
    private String userName;
    @Basic
    @Column(name = "Password_Encrypt", length = 200)
    private String passwordEncrypt;
    @Basic
    @Column(name = "Email", length = 50)
    private String email;
    @Basic
    @Column(name = "FIRST_NAME", columnDefinition = "nvarchar(255)")
    private String firstName;
    @Column(name = "LAST_NAME", columnDefinition = "nvarchar(255)")
    private String lastName;
    @Basic
    @Column(name = "URL_AVATAR", length = 300)
    private String urlAvatar;
    @Basic
    @Column(name = "ID_ADDRESS", length = 30)
    private String idAddress;
    @Basic
    @Column(name = "DATE_OF_BIRTH")
    private Date dateOfBirth;
    @Basic
    @Column(name = "DESCRIBE", columnDefinition = "nvarchar(300)")
    private String describe;
    @Basic
    @Column(name = "ACCESS_FAILED_COUNT")
    private Integer accessFailedCount;
    @Basic
    @Column(name = "EMAIL_CONFIRMED")
    private String emailConfirmed;
    @Basic
    @Column(name = "IS_ONLINE")
    private String isOnline;
    @Basic
    @Column(name = "STATUS")
    private Integer status;
    @Basic
    @Column(name = "GENDER")
    private String genderValue;
    @Column(name = "URL_IMAGE_COVER", nullable = false, length = 200)
    private String urlImgCover;
    @Column(name = "IS_ACCOUNT_NON_LOCKED")
    private String isAccountNonLocked;
    @Column(name = "PROVIDE")
    private String provide;
    @Column(name = "IS_COMPLETE", columnDefinition = "VARCHAR(5) DEFAULT 'N'")
    private String isComplete;
    @Column(name="IS_LOCK")
    private String isLock;
    @Column(name="LOCK_BY")
    private String lockBy;
    @Column(name = "TIME_LOCK")
    @UpdateTimestamp
    private String timeLock;
    @Column(name="RELATIONSHIP_STATUS")
    private String relationshipStatus;
    public User(String userName, String passwordEncrypt, String firstName,String lastName, String provide) {
        this.userName = userName;
        this.passwordEncrypt = passwordEncrypt;
        this.firstName = firstName;
        this.lastName=lastName;
        this.setIsDeleted(Constant.STATUS.NO);
        this.accessFailedCount = 0;
        this.emailConfirmed = Constant.STATUS.NO;
        this.isOnline = Constant.STATUS.NO;
        this.status = 0;
        this.describe = "";
        this.email = "";
        this.urlAvatar = "https://lh3.google.com/u/0/d/1ZffstBnAUUI1LvpVRTHsYqgpkDmRDBLB";
        this.urlImgCover = "";
        this.isAccountNonLocked = Constant.STATUS.NO;
        this.provide = provide;
        this.isComplete= Constant.STATUS.NO;
        this.isLock=Constant.STATUS.NO;
    }

    public User(String userName, String passwordEncrypt, String firstName,String lastName,
                String email, String urlAvatar, String provide) {
        this.userName = userName;
        this.passwordEncrypt = passwordEncrypt;
        this.firstName= firstName;
        this.lastName=lastName;
        this.setIsDeleted(Constant.STATUS.NO);
        this.accessFailedCount = 0;
        this.emailConfirmed = Constant.STATUS.NO;
        this.isOnline = Constant.STATUS.NO;
        this.status = 0;
        this.describe = "";
        this.email = email;
        this.urlAvatar = urlAvatar;
        this.urlImgCover = "";
        this.isAccountNonLocked = Constant.STATUS.NO;
        this.provide = provide;
        this.isComplete=Constant.STATUS.NO;
        this.isLock=Constant.STATUS.NO;
    }
}
