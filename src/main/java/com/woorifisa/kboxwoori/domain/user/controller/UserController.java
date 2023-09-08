package com.woorifisa.kboxwoori.domain.user.controller;

import com.woorifisa.kboxwoori.domain.event.exception.WooriLinkRequiredException;
import com.woorifisa.kboxwoori.domain.user.dto.UserAddrResponseDto;
import com.woorifisa.kboxwoori.domain.user.dto.UserInfoRequestDto;
import com.woorifisa.kboxwoori.domain.user.dto.UserInfoResponseDto;
import com.woorifisa.kboxwoori.domain.user.dto.UserPageResponseDto;
import com.woorifisa.kboxwoori.domain.user.service.UserService;
import com.woorifisa.kboxwoori.global.response.ResponseDto;
import com.woorifisa.kboxwoori.global.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static com.woorifisa.kboxwoori.global.util.AuthenticationUtil.getCurrentUserId;
import static com.woorifisa.kboxwoori.global.util.AuthenticationUtil.isWooriLinked;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @DeleteMapping
    public ResponseDto cancleMembership() {
        userService.deleteUser(getCurrentUserId());
        return ResponseDto.success();
    }

    @GetMapping
    public ResponseDto<UserInfoResponseDto> showEditForm() {
        UserInfoResponseDto updateUserResponseDTO = userService.findUser(getCurrentUserId());
        return ResponseDto.success(updateUserResponseDTO);
    }

    @PutMapping
    public ResponseDto EditUserInfo(@Valid @RequestBody UserInfoRequestDto userDto) {
        userService.updateUserInfo(getCurrentUserId(), userDto);
        return ResponseDto.success();
    }

    @PostMapping("/logout")
    public ResponseDto logout(HttpServletRequest request) {
        userService.logout(getCurrentUserId(), jwtUtil.resolveToken(request));
        return ResponseDto.success();
    }

    @GetMapping("/mypage")
    public ResponseDto mypage() {
        UserPageResponseDto userPageResponseDto = userService.myPageUserInfo(getCurrentUserId());
        return ResponseDto.success(userPageResponseDto);
    }

    @GetMapping ("/addr")
    public ResponseDto<UserAddrResponseDto> getAddress() {
        return ResponseDto.success(userService.getAddress(getCurrentUserId()));
    }

    @GetMapping("/woori")
    public ResponseDto getWooriLinked() {
        if (!isWooriLinked()) {
            throw WooriLinkRequiredException.EXCEPTION;
        }
        return ResponseDto.success();
    }
}