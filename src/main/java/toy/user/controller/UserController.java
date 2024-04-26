package toy.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import toy.user.Entity.User;
import toy.user.dto.ApiResponse;
import toy.user.dto.RequestDto;
import toy.user.service.UserService;
import org.springframework.validation.BindingResult;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;


    @Operation
    // 회원 가입
    @PostMapping("/join")
    public ResponseEntity<ApiResponse<String>> signup(
            @Valid @RequestBody RequestDto requestDto, BindingResult bindingResult) {
        return userService.createUser(requestDto, bindingResult);
    }

    //회원정보조회
    @GetMapping("/list")
    public ResponseEntity<List<User>> listUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "userid") String sort) {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    //회원정보수정
    @PutMapping("/{userid}")
    public ResponseEntity<String> updateUser(@PathVariable String userid, @RequestBody User user) {
        userService.updateUser(userid, user);
        return ResponseEntity.status(HttpStatus.OK).body("회원 정보 수정 완료");
    }
}