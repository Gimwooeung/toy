package toy.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toy.user.Entity.User;


public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUserid(String userid);
}
