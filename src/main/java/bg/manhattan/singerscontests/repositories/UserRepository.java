package bg.manhattan.singerscontests.repositories;

import bg.manhattan.singerscontests.model.entity.User;
import bg.manhattan.singerscontests.model.enums.UserRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    @Query( "SELECT DISTINCT u " +
            "FROM User u JOIN u.roles r " +
            "WHERE EXISTS (" +
            "SELECT us " +
            "FROM User us LEFT JOIN us.roles rl " +
            "WHERE us.id = u.id AND rl.userRole = :role)")
    List<User> findByRole(@Param("role") UserRoleEnum role);

    @Query( "SELECT DISTINCT u " +
            "FROM User u LEFT JOIN u.roles r " +
            "WHERE NOT EXISTS (" +
            "SELECT us " +
            "FROM User us JOIN us.roles rl " +
            "WHERE us.id = u.id AND rl.userRole = :role)")
    List<User> findNotInRole(@Param("role") UserRoleEnum role);

    @Query( "SELECT u " +
            "FROM User u JOIN u.roles r " +
            "WHERE u.id = :id AND r.userRole = :role")
    List<User> findByRoleAndId(@Param("role") UserRoleEnum role, @Param("id") Long id);
}
