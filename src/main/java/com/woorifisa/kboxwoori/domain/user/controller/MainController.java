package com.woorifisa.kboxwoori.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MainController {

    @GetMapping("/")
    public String main(){
        System.out.println("--------메인페이지입니다-----------");
        return "메인페이지입니다.";
    }

    @GetMapping("/session-expired")
    public String expired(){
        return "세션 만료";
    }
}
