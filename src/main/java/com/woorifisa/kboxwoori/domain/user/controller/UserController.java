package com.woorifisa.kboxwoori.domain.user.controller;

import com.woorifisa.kboxwoori.domain.user.dto.UserInfoResponseDTO;
import com.woorifisa.kboxwoori.domain.user.dto.UserDTO;
import com.woorifisa.kboxwoori.domain.user.dto.UserPointResponseDTO;
import com.woorifisa.kboxwoori.domain.user.exception.NotAuthenticatedAccountException;
import com.woorifisa.kboxwoori.domain.user.service.UserService;
import com.woorifisa.kboxwoori.global.config.security.PrincipalDetails;
import com.woorifisa.kboxwoori.global.response.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping({"", "/"})
    public String index(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        System.out.println(principalDetails.isWooriLinked());
        return "메인페이지";
    }

    @PostMapping("/api/join")
    public String join(@RequestBody UserDTO userDto){
        userService.join(userDto);
        return "회원가입이 완료되었습니다!";
    }

    @DeleteMapping("/api/users")
    public String cancleMembership(@AuthenticationPrincipal PrincipalDetails pdetails, HttpServletRequest request){

        boolean result = userService.deleteUser(pdetails.getUsername());

        if(result){
            HttpSession session = request.getSession(false);
            SecurityContextHolder.clearContext();
            if(session != null){
                session.invalidate();
            }
            return "탈퇴 성공!";
        }
        return "탈퇴 처리에 실패하였습니다.";
    }

    @GetMapping("/api/users")
    public UserInfoResponseDTO showEditForm(@AuthenticationPrincipal PrincipalDetails pdetails){
        UserInfoResponseDTO updateUserResponseDTO = userService.findUser(pdetails.getUsername());
        return updateUserResponseDTO;
    }

    @PutMapping("/api/users")
    public UserInfoResponseDTO EditUserInfo(@AuthenticationPrincipal PrincipalDetails pdetails, @RequestBody UserInfoResponseDTO userDto){
        UserInfoResponseDTO updateUserResponseDTO = userService.updateUserInfo(pdetails.getUsername(), userDto);
        return updateUserResponseDTO;
    }

    @GetMapping("/api/point/honeymoney")
    public UserPointResponseDTO getUserPoint(@AuthenticationPrincipal PrincipalDetails pdetails){
        UserPointResponseDTO userPointResponseDTO = userService.getUserPoint(pdetails.getUsername());
        return userPointResponseDTO;
    }

    @GetMapping("/api/users/woori")
    public ResponseDto IsWooriLinked(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        if (principalDetails == null) {
            throw NotAuthenticatedAccountException.EXCEPTION;
        }
        userService.IsWooriLinked(principalDetails.getUsername());
        return ResponseDto.success();
    }
    
}
