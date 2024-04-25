package toy.user.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String userid;

    @Column(nullable = false, unique = false)
    private String password;

    @Column(nullable = false, unique = false)
    private String nickname;

    @Column(nullable = false, unique = false)
    private String name;

    @Column(nullable = false, unique = false)
    private String tel;

    @Column(nullable = false, unique = false)
    private String email;

    public User(String userid, String password, String name, String nickname, String tel, String email) {
        this.userid = userid;
        this.password = password;
        this.nickname = nickname;
        this.name = name;
        this.tel = tel;
        this.email = email;
    }

}
