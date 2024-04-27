package toy.user.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.server.ResponseStatusException;
import toy.user.Entity.User;
import toy.user.dto.ApiResponse;
import toy.user.dto.RequestDto;
import toy.user.dto.UpdateRequestDto;
import toy.user.exception.CustomException;
import toy.user.exception.ErrorCode;
import toy.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 회원가입
    public ResponseEntity<ApiResponse<String>> join(RequestDto requestDto, BindingResult bindingResult) {
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
        String email = requestDto.getEmail();
        String password = requestDto.getPassword();
        String nickname = requestDto.getNickname();
        String name = requestDto.getName();
        String tel = requestDto.getTel();

        // 예외처리 아이디 중복
        Optional<User> checkUsername = userRepository.findByUserid(userid);
        if (checkUsername.isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATE_USERID);
        }

        // 예외처리 이메일 중복
        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
        }

        User user = new User(userid, password, name, nickname, tel, email);
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.successMessage("회원가입이 완료되었습니다."));
    }

    // 회원 정보 조회 (페이지네이션 및 정렬 기능 추가)
    public List<User> getAllUsers(int page, int pageSize, String sortBy1, String sortOrder1, String sortBy2, String sortOrder2) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortOrder1), sortBy1)
                .and(Sort.by(Sort.Direction.fromString(sortOrder2), sortBy2));
        Pageable pageable = PageRequest.of(page, pageSize, sort);
        Page<User> userPage = userRepository.findAll(pageable);
        return userPage.getContent();
    }

    // 회원 정보 수정
    public ResponseEntity<?> updateUser(String userid, UpdateRequestDto requestDto, BindingResult bindingResult) {
        // userid로 사용자 조회
        User user = userRepository.findByUserid(userid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));

        // email 변수 선언
        String email = user.getEmail();

        // 유효성 검사
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if (!fieldErrors.isEmpty()) {
            List<String> errorMessages = new ArrayList<>();
            for (FieldError fieldError : fieldErrors) {
                errorMessages.add(fieldError.getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(ApiResponse.error(String.join(", ", errorMessages)));
        }

        // 예외처리 이메일 중복
        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
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
}