package com.woorifisa.kboxwoori.global.exception.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woorifisa.kboxwoori.global.exception.CustomExceptionStatus;
import com.woorifisa.kboxwoori.global.response.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.warn("{} : Exception transmitted to AccessDeniedHandler", LocalDateTime.now());

        response.setContentType("application/json;charset=UTF-8");
        response.sendError(HttpServletResponse.SC_FORBIDDEN);
        ResponseDto error = ResponseDto.error(CustomExceptionStatus.ACCOUNT_ACCESS_DENIED);
        new ObjectMapper().writeValue(response.getWriter(), error);
    }
}
