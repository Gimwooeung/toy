package toy.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import toy.user.Entity.User;
import toy.user.dto.ApiResponse;
import toy.user.dto.RequestDto;
import toy.user.dto.UpdateRequestDto;
import toy.user.service.UserService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;


    @Operation(summary = "회원 가입", description = "회원 가입")
    // 회원 가입
    @PostMapping("/join")
    public ResponseEntity<ApiResponse<String>> join(
            @Valid @RequestBody RequestDto requestDto, BindingResult bindingResult) {
        return userService.join(requestDto, bindingResult);
    }

    @Operation(summary = "회원 정보 조회", description = "회원 정보 조회, page 0 시작, pageSize : 3개, 정렬 CreatedAt내림차순, userid오름차순")
    @GetMapping("/list")
    public ResponseEntity<List<User>> listUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int pageSize,
            @RequestParam(defaultValue = "createdAt") String sortBy1,
            @RequestParam(defaultValue = "desc") String sortOrder1,
            @RequestParam(defaultValue = "userid") String sortBy2,
            @RequestParam(defaultValue = "asc") String sortOrder2) {
        List<User> users = userService.getAllUsers(page, pageSize, sortBy1, sortOrder1, sortBy2, sortOrder2);
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "회원 정보 수정", description = "회원 정보 수정")
    //회원정보수정
    @PutMapping("/{userid}")
    public ResponseEntity<?> updateUser(@PathVariable("userid") String userid, @Valid @RequestBody UpdateRequestDto requestDto, BindingResult bindingResult) {
        return userService.updateUser(userid, requestDto, bindingResult);
    }
}