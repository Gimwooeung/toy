package toy.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import toy.user.Entity.User;
import toy.user.dto.ApiResponse;
import toy.user.dto.RequestDto;
import toy.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    //회원가입
    public ResponseEntity<ApiResponse<String>> createUser(RequestDto requestDto, BindingResult bindingResult) {
        // 사용자 아이디 중복 확인
        if (isUserIdExists(requestDto.getUserid())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error("이미 존재하는 사용자 아이디입니다."));
        }

        // 유효성 검사
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if (!fieldErrors.isEmpty()) {
            List<String> errorMessages = new ArrayList<>();
            for (FieldError fieldError : fieldErrors) {
                errorMessages.add(fieldError.getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(ApiResponse.error(String.join(", ", errorMessages)));
        }

        // 사용자 정보 저장
        String userid = requestDto.getUserid();
        String password = requestDto.getPassword();
        String nickname = requestDto.getNickname();
        String name = requestDto.getName();
        String tel = requestDto.getTel();
        String email = requestDto.getEmail();

        User user = new User(userid, password, name, nickname, tel, email);

        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.successMessage("회원가입이 완료되었습니다."));
    }


    //회원정보조회
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    //회원정보수정
    public void updateUser(String userid, User user) {
        user.setUserid(userid);
        userRepository.save(user);
    }

    // 사용자 아이디 중복 확인
    public boolean isUserIdExists(String userid) {
        return userRepository.existsByUserid(userid);
    }
}
