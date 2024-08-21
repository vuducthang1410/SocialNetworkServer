package wint.webchat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import wint.webchat.entities.user.Role;

import java.util.List;

public interface RoleRepositoryJPA extends JpaRepository<Role, String> {
    String query = """
            select r.id,r.role_name
            from role r join user_role ur on r.id= ur.role_id
                  join dbo.tbl_user tu on ur.user_id = tu.id
            where tu.user_name=:username
            """;

    List<Role> findRoleByRoleName(String roleName);

    @Query(value = query, nativeQuery = true)
    List<Role> findRoleByUsername(String username);
}
