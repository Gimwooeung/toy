package toy.user.dto;

import lombok.Getter;

@Getter
public class RequestDto {
    private Long id;
    private String password;
    private String nickname;
    private String name;
    private String tel;
    private String email;
}