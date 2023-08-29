package com.woorifisa.kboxwoori.domain.user.controller;

import com.woorifisa.kboxwoori.domain.user.dto.UserInfoResponseDto;
import com.woorifisa.kboxwoori.domain.user.dto.UserDto;
import com.woorifisa.kboxwoori.domain.user.dto.UserPageResponseDto;
import com.woorifisa.kboxwoori.domain.user.exception.NotAuthenticatedAccountException;
import com.woorifisa.kboxwoori.domain.user.service.UserService;
import com.woorifisa.kboxwoori.global.config.security.PrincipalDetails;
import com.woorifisa.kboxwoori.global.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping({"", "/"})
    public String index(@AuthenticationPrincipal PrincipalDetails pdetails){
        System.out.println(pdetails.isWooriLinked());
        return "메인페이지";
    }

    @PostMapping("/api/join")
    public ResponseDto join(@RequestBody UserDto userDto){
        userService.join(userDto);
        return ResponseDto.success();
    }

    @DeleteMapping("/api/users")
    public ResponseDto cancleMembership(@AuthenticationPrincipal PrincipalDetails pdetails, HttpServletRequest request){
        boolean result = userService.deleteUser(pdetails.getUsername());
        if(result){
            HttpSession session = request.getSession(false);
            SecurityContextHolder.clearContext();
            if(session != null){
                session.invalidate();
            }
        }
        return ResponseDto.success();
    }

    @GetMapping("/api/users")
    public ResponseDto<UserInfoResponseDto> showEditForm(@AuthenticationPrincipal PrincipalDetails pdetails){
        UserInfoResponseDto updateUserResponseDTO = userService.findUser(pdetails.getUsername());
        return ResponseDto.success(updateUserResponseDTO);
    }

    @PutMapping("/api/users")
    public ResponseDto<UserInfoResponseDto> EditUserInfo(@AuthenticationPrincipal PrincipalDetails pdetails, @RequestBody UserInfoResponseDto userDto){
        UserInfoResponseDto updateUserResponseDTO = userService.updateUserInfo(pdetails, userDto);
        return ResponseDto.success(updateUserResponseDTO);
    }

    @GetMapping("/api/users/woori")
    public ResponseDto IsWooriLinked(@AuthenticationPrincipal PrincipalDetails pdetails){
        if (pdetails == null) {
            throw NotAuthenticatedAccountException.EXCEPTION;
        }
        userService.IsWooriLinked(pdetails.getUsername());
        return ResponseDto.success();
    }

    @GetMapping("/api/users/mypage")
    public ResponseDto mypage(@AuthenticationPrincipal PrincipalDetails pdetails){
        UserPageResponseDto userPageResponseDto = userService.myPageUserInfo(pdetails.getUsername());
        return ResponseDto.success(userPageResponseDto);
    }
    
}