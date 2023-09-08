package com.woorifisa.kboxwoori.global.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woorifisa.kboxwoori.global.exception.CustomExceptionStatus;
import com.woorifisa.kboxwoori.global.response.ResponseDto;
import com.woorifisa.kboxwoori.global.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtUtil.resolveToken(request);

        try {
            if (token != null && jwtUtil.verifyToken(token)) {
                String isLogout = redisTemplate.opsForValue().get(token);

                if (ObjectUtils.isEmpty(isLogout)) {
                    Authentication authentication = jwtUtil.getAuthentication(token);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (ExpiredJwtException e) {
            setResponse(CustomExceptionStatus.ALREADY_EXPIRED_JWT, response);
        } catch (JwtException e) {
            setResponse(CustomExceptionStatus.INVALID_JWT, response);
        }

        filterChain.doFilter(request, response);
    }

    private void setResponse(CustomExceptionStatus status, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        ResponseDto error = ResponseDto.error(status);
        new ObjectMapper().writeValue(response.getWriter(), error);
    }
}
