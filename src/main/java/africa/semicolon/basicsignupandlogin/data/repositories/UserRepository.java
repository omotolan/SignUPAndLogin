package africa.semicolon.basicsignupandlogin.data.repositories;

import africa.semicolon.basicsignupandlogin.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmailIgnoreCase(String email);
}
