package com.woorifisa.kboxwoori.domain.user.controller;

import com.woorifisa.kboxwoori.domain.user.dto.TokenDto;
import com.woorifisa.kboxwoori.domain.user.dto.UserDto;
import com.woorifisa.kboxwoori.domain.user.dto.UserLoginRequestDto;
import com.woorifisa.kboxwoori.domain.user.service.UserService;
import com.woorifisa.kboxwoori.global.exception.CustomExceptionStatus;
import com.woorifisa.kboxwoori.global.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseDto join(@Valid @RequestBody UserDto userDto) {
        userService.join(userDto);
        return ResponseDto.success();
    }

    @GetMapping("/check")
    public ResponseDto checkIdDuplication(@RequestParam(value="userId") String userId) {
        System.out.println(userId);
        if(userService.existsByUserId(userId)){
            return ResponseDto.error(CustomExceptionStatus.DUPLICATED_USERID);
        }
        return ResponseDto.success();
    }

    @PostMapping("/login")
    public ResponseDto<TokenDto> login(@Valid @RequestBody UserLoginRequestDto userLoginRequestDto) {
        TokenDto tokenDto = userService.login(userLoginRequestDto);
        return ResponseDto.success(tokenDto);
    }

    @PostMapping("/refresh")
    public ResponseDto<TokenDto> refreshToken(HttpServletRequest request) {
        return ResponseDto.success(userService.refreshToken(request));
    }


}
