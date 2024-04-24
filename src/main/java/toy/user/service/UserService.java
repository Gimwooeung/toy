package toy.user.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import toy.user.Entity.User;
import toy.user.dto.RequestDto;
import toy.user.dto.ResponseDto;
import toy.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseDto join(RequestDto requestDto) {
        User user = new User(requestDto);
        User saveUser = userRepository.save(user);
        return new ResponseDto(saveUser);
    }

    public List<ResponseDto> getAllUserList() {
        return userRepository.findAll().stream()
                .map(ResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public User updateUser(Long id, RequestDto requestDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("선택한 id는 존재하지 않습니다."));
        user.update(requestDto);
        return user;
    }
}
