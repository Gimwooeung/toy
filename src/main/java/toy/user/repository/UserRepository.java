package toy.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toy.user.Entity.User;

import java.util.List;


public interface UserRepository extends JpaRepository <User, Long>{
    List<User> findAll();
}
