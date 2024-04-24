package toy.user.dto;

import lombok.Getter;
import toy.user.Entity.User;


@Getter
public class ResponseDto {
    private final Long id;
    private final String password;
    private final String nickname;
    private final String name;
    private final String tel;
    private final String email;

    public ResponseDto(User user) {
        this.id = user.getId();
        this.password = user.getPassword();
        this.nickname = user.getNickname();
        this.name = user.getName();
        this.tel = user.getTel();
        this.email = user.getEmail();
    }

}