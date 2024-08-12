package wint.webchat.entities.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.io.Serializable;
import java.sql.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_user")
public class User implements Serializable {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    private String id;
    @Column(name = "USER_NAME", nullable = false, length = 50)
    private String userName;
    @Basic
    @Column(name = "Password_Encrypt", nullable = true, length = 200)
    private String passwordEncrypt;
    @Basic
    @Column(name = "Email", nullable = true, length = 50)
    private String email;
    @Basic
    @Column(name = "FULL_NAME", nullable = true, columnDefinition = "nvarchar(255)")
    private String fullName;
    @Basic
    @Column(name = "URL_AVATAR", nullable = true, length = 300)
    private String urlAvatar;
    @Basic
    @Column(name = "ID_ADDRESS", nullable = true, length = 30)
    private String idAddress;
    @Basic
    @Column(name = "DATE_OF_BIRTH", nullable = true)
    private Date dateOfBirth;
    @Basic
    @Column(name = "DESCRIBE", nullable = true, columnDefinition = "nvarchar(300)")
    private String describe;
    @Basic
    @Column(name = "IS_DELETE", nullable = true)
    private Boolean isDelete;
    @Basic
    @Column(name = "ACCESS_FAILED_COUNT", nullable = true)
    private Integer accessFailedCount;
    @Basic
    @Column(name = "EMAIL_CONFIRMED", nullable = true)
    private Boolean emailConfirmed;
    @Basic
    @Column(name = "IS_ONLINE", nullable = true)
    private Boolean isOnline;
    @Basic
    @Column(name = "STATUS_ACCOUNT", nullable = true)
    private Boolean statusAccount;
    @Basic
    @Column(name = "GENDER")
    private String genderValue;
    @Column(name = "URL_IMAGE_COVER", nullable = false, length = 255)
    private String urlImgCover;
    @Column(name = "IS_ACCOUNT_NON_LOCKED")
    private Boolean isAccountNonLocked;
    @Column(name = "PROVIDE")
    private String provide;
    @Column(name = "CREATE_TIME")
    @CreationTimestamp
    private String createTime;

    public User(String userName, String passwordEncrypt, String fullName, String provide) {
        this.userName = userName;
        this.passwordEncrypt = passwordEncrypt;
        this.fullName = fullName;
        this.isDelete = false;
        this.accessFailedCount = 0;
        this.emailConfirmed = false;
        this.isOnline = true;
        this.statusAccount = true;
        this.describe = "";
        this.email = "email";
        this.urlAvatar = "https://lh3.google.com/u/0/d/1ZffstBnAUUI1LvpVRTHsYqgpkDmRDBLB";
        this.urlImgCover = "";
        this.isAccountNonLocked = true;
        this.provide = provide;
    }

    public User(String userName, String passwordEncrypt, String fullName,
                String email, String urlAvatar, String provide) {
        this.userName = userName;
        this.passwordEncrypt = passwordEncrypt;
        this.fullName = fullName;
        this.isDelete = false;
        this.accessFailedCount = 0;
        this.emailConfirmed = false;
        this.isOnline = true;
        this.statusAccount = true;
        this.describe = "";
        this.email = email;
        this.urlAvatar = urlAvatar;
        this.urlImgCover = "";
        this.isAccountNonLocked = true;
        this.provide = provide;
    }
}
