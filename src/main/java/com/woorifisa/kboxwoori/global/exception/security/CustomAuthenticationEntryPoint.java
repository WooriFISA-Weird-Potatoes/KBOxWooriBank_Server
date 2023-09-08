package com.woorifisa.kboxwoori.global.exception.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woorifisa.kboxwoori.global.exception.CustomExceptionStatus;
import com.woorifisa.kboxwoori.global.response.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.warn("{} : Exception transmitted to AuthenticationEntryPoint", LocalDateTime.now());
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        ResponseDto error = ResponseDto.error(CustomExceptionStatus.NOT_AUTHENTICATED_ACCOUNT);
        new ObjectMapper().writeValue(response.getWriter(), error);
    }
}
