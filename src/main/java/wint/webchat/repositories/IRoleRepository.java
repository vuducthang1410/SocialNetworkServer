package wint.webchat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import wint.webchat.entities.user.Role;

import java.util.Optional;
@Transactional
public interface IRoleRepository extends JpaRepository<Role,Long> {
    @Query("""
            select r from Role r where r.roleName = :nameRole
            """)
    Optional<Role> findByRoleName(@Param("nameRole") String nameRole);
}
