package com.woorifisa.kboxwoori.domain.user.controller;

import com.woorifisa.kboxwoori.domain.user.dto.UpdateUserResponseDTO;
import com.woorifisa.kboxwoori.domain.user.dto.UserDTO;
import com.woorifisa.kboxwoori.domain.user.service.UserService;
import com.woorifisa.kboxwoori.global.config.security.PrincipalDetails;
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
    public UpdateUserResponseDTO showEditForm(@AuthenticationPrincipal PrincipalDetails pdetails){
        UpdateUserResponseDTO updateUserResponseDTO = userService.findUser(pdetails.getUsername());
        return updateUserResponseDTO;
    }

    @PutMapping("/api/users")
    public String EditUserInfo(@AuthenticationPrincipal PrincipalDetails pdetails, @RequestBody UpdateUserResponseDTO userDto){
        UpdateUserResponseDTO updateUserResponseDTO = userService.updateUser(pdetails.getUsername(), userDto);
        return "수정 성공!";
    }




}
