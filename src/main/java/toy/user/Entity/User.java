package toy.user.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import toy.user.dto.RequestDto;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    public User(RequestDto requestDto) {
        this.password = requestDto.getPassword();
        this.nickname = requestDto.getNickname();
        this.name = requestDto.getName();
        this.tel = requestDto.getTel();
        this.email = requestDto.getEmail();
    }

    public void update(RequestDto requestDto) {
        this.nickname = requestDto.getNickname();
        this.name = requestDto.getName();
        this.tel = requestDto.getTel();
        this.email = requestDto.getEmail();
    }
}
