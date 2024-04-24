package toy.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;
import toy.user.Entity.User;
import toy.user.dto.RequestDto;
import toy.user.dto.ResponseDto;
import toy.user.service.UserService;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("api")
public class UserController {
    private final UserService userService;

    //회원가입
    @PostMapping("/user/join")
    public ResponseDto join(@RequestBody RequestDto requestDto) {
        return userService.join(requestDto);
    }

    // 회원 목록 조회
    @GetMapping("/user/list")
    public List<ResponseDto> getAllUserList() {
        return userService.getAllUserList();
    }

    // 회원 정보 수정
    @PutMapping("/user/{id}")
    public ResponseDto updateUser(@PathVariable Long id, @RequestBody RequestDto requestDto) {
        User updateUser = userService.updateUser(id, requestDto);
        ResponseDto responseDto = new ResponseDto(updateUser);
        return responseDto;
    }
}