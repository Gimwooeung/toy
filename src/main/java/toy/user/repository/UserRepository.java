package toy.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toy.user.Entity.User;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserid(String userid);

    Optional<User> findByEmail(String email);
}