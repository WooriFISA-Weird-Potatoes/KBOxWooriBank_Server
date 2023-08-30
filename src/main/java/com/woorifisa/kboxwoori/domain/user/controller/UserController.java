package com.woorifisa.kboxwoori.domain.user.controller;

import com.woorifisa.kboxwoori.domain.user.dto.UserInfoResponseDto;
import com.woorifisa.kboxwoori.domain.user.dto.UserDto;
import com.woorifisa.kboxwoori.domain.user.dto.UserPageResponseDto;
import com.woorifisa.kboxwoori.domain.user.exception.NotAuthenticatedAccountException;
import com.woorifisa.kboxwoori.domain.user.service.UserService;
import com.woorifisa.kboxwoori.global.config.security.PrincipalDetails;
import com.woorifisa.kboxwoori.global.exception.CustomExceptionStatus;
import com.woorifisa.kboxwoori.global.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseDto join(@Valid @RequestBody UserDto userDto){
        userService.join(userDto);
        return ResponseDto.success();
    }

    @GetMapping("/check")
    public ResponseDto checkIdDuplication(@RequestParam(value="userId") String userId){
        System.out.println(userId);
        if(userService.existsByUserId(userId)){
            return ResponseDto.error(CustomExceptionStatus.DUPLICATED_USERID);
        }
        return ResponseDto.success();
    }

    @DeleteMapping
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

    @GetMapping
    public ResponseDto<UserInfoResponseDto> showEditForm(@AuthenticationPrincipal PrincipalDetails pdetails){
        UserInfoResponseDto updateUserResponseDTO = userService.findUser(pdetails.getUsername());
        return ResponseDto.success(updateUserResponseDTO);
    }

    @PutMapping
    public ResponseDto<UserInfoResponseDto> EditUserInfo(@AuthenticationPrincipal PrincipalDetails pdetails, @Valid @RequestBody UserInfoResponseDto userDto){
        UserInfoResponseDto updateUserResponseDTO = userService.updateUserInfo(pdetails, userDto);
        return ResponseDto.success(updateUserResponseDTO);
    }

    @GetMapping("/woori")
    public ResponseDto IsWooriLinked(@AuthenticationPrincipal PrincipalDetails pdetails){
        if (pdetails == null) {
            throw NotAuthenticatedAccountException.EXCEPTION;
        }
        userService.IsWooriLinked(pdetails.getUsername());
        return ResponseDto.success();
    }

    @GetMapping("/mypage")
    public ResponseDto mypage(@AuthenticationPrincipal PrincipalDetails pdetails){
        UserPageResponseDto userPageResponseDto = userService.myPageUserInfo(pdetails.getUsername());
        return ResponseDto.success(userPageResponseDto);
    }
    
}