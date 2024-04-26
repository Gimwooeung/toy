package toy.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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


    // 회원정보 조회 (페이지네이션 및 정렬 기능 추가)
    public List<User> getAllUsers(int page, int pageSize, String sortBy1, String sortOrder1, String sortBy2, String sortOrder2) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortOrder1), sortBy1)
                .and(Sort.by(Sort.Direction.fromString(sortOrder2), sortBy2));
        Pageable pageable = PageRequest.of(page, pageSize, sort);
        Page<User> userPage = userRepository.findAll(pageable);
        return userPage.getContent();
    }



    public ResponseEntity<?> updateUser(String userid, RequestDto requestDto) {
        // 해당 userid를 가진 사용자 정보를 조회
        User user = userRepository.findByUserid(userid);

        // 사용자가 존재하지 않는 경우
        if (user == null) {
            return ResponseEntity.badRequest().body(ApiResponse.error("해당하는 사용자가 없습니다."));
        }

        // 사용자 정보 업데이트
        user.setPassword(requestDto.getPassword());
        user.setName(requestDto.getName());
        user.setNickname(requestDto.getNickname());
        user.setTel(requestDto.getTel());
        user.setEmail(requestDto.getEmail());

        // 수정된 사용자 정보 저장
        userRepository.save(user);

        // 성공 메시지 반환
        return ResponseEntity.ok(ApiResponse.successMessage("사용자 정보가 성공적으로 수정되었습니다."));
    }

    // 사용자 아이디 중복 확인
    public boolean isUserIdExists(String userid) {
        return userRepository.existsByUserid(userid);
    }
}
