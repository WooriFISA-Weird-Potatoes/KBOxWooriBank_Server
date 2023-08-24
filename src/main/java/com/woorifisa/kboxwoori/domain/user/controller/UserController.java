package com.woorifisa.kboxwoori.domain.user.controller;

import com.woorifisa.kboxwoori.domain.user.dto.UserDTO;
import com.woorifisa.kboxwoori.domain.user.service.UserService;
import com.woorifisa.kboxwoori.global.config.security.PrincipalDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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


}
