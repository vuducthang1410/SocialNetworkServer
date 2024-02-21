package wint.webchat.entities.user;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import wint.webchat.entities.conversation.MemberConversation;
import wint.webchat.entities.group.MemberGroup;
import wint.webchat.entities.post.Comment;
import wint.webchat.entities.post.Interact;
import wint.webchat.entities.post.Post;

import java.io.Serializable;
import java.sql.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "[User]")
public class User  implements Serializable {
    @Id
    @Column(name = "user_name", nullable = false, length = 50)
    private String userName;
    @Basic
    @Column(name = "PasswordEncrypt", nullable = true, length = 200)
    private String passwordEncrypt;
    @Basic
    @Column(name = "Email", nullable = true, length = 50)
    private String email;
    @Basic
    @Column(name = "FullName", nullable = true,columnDefinition = "nvarchar(255)")
    private String fullName;
    @Basic
    @Column(name = "UrlAvatar", nullable = true, length = 300)
    private String urlAvatar;
    @Basic
    @Column(name = "id_address", nullable = true, length = 30)
    private String idAddress;
    @Basic
    @Column(name = "DateOfBirth", nullable = true)
    private Date dateOfBirth;
    @Basic
    @Column(name = "Describe", nullable = true,columnDefinition = "nvarchar(300)")
    private String describe;
    @Basic
    @Column(name = "is_delete", nullable = true)
    private Boolean isDelete;
    @Basic
    @Column(name = "AccessFailedCount", nullable = true)
    private Integer accessFailedCount;
    @Basic
    @Column(name = "EmailConfirmed", nullable = true)
    private Boolean emailConfirmed;
    @Basic
    @Column(name = "is_online", nullable = true)
    private Boolean isOnline;
    @Basic
    @Column(name = "status_account", nullable = true)
    private Boolean statusAccount;
    @Basic
    @Column(name = "gender")
    private Boolean genderValue;

    @OneToMany(mappedBy = "userRole",fetch = FetchType.EAGER)
    private Set<UserRole> userRoleList;
    @OneToMany(mappedBy = "userCreatePost")
    private Set<Post> listPost;
    @OneToMany(mappedBy = "userComment")
    private Set<Comment> listComment;
    @OneToMany(mappedBy = "userMember")
    private Set<MemberGroup> listMemberGroup;
    @OneToMany(mappedBy = "userInteract")
    private Set<Interact> listPostInteract;
    @OneToMany(mappedBy = "userInvitationSender")
    private Set<Friend> listInvitationSender;
    @OneToMany(mappedBy = "userInvitationReceiver")
    private Set<Friend> listInvitationReceiver;
    @OneToMany(mappedBy = "memberConversation")
    private Set<MemberConversation>  listMemberConversation;
    @OneToMany(mappedBy = "userSearch")
    private Set<Search> listSearch;

    public User(String userName, String passwordEncrypt, String fullName) {
        this.userName = userName;
        this.passwordEncrypt = passwordEncrypt;
        this.fullName= fullName;
        this.isDelete = false;
        this.accessFailedCount = 0;
        this.emailConfirmed = false;
        this.isOnline = true;
        this.statusAccount = true;
        this.describe="";
        this.email="";
        this.urlAvatar="https://drive.google.com/uc?id=1ZffstBnAUUI1LvpVRTHsYqgpkDmRDBLB";
    }
}
