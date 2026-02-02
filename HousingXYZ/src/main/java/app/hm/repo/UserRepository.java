package app.hm.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import app.hm.entity.User;
import app.hm.enums.UserRole;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    List<User> findByUserRole(UserRole role);

    // Optional: only enabled approvers
    List<User> findByUserRoleAndEnabledTrue(UserRole role);
    boolean existsByNidNumber(String nidNumber);
}



