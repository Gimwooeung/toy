package toy.user.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@RequiredArgsConstructor
public class ResponseDto {
    private final Long id;
    private final String password;
    private final String nickname;
    private final String name;
    private final String tel;
    private final String email;

}