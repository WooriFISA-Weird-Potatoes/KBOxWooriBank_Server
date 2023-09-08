package com.woorifisa.kboxwoori.global.util;

import com.woorifisa.kboxwoori.domain.user.dto.TokenDto;
import com.woorifisa.kboxwoori.global.config.security.CustomUserDetailService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final RedisTemplate<String, String> redisTemplate;
    private final CustomUserDetailService customUserDetailService;

    //TODO: Value로 변경
    @Value("${jwt.secret}")
    private String secretKey;
    private int ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30;
    private int REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;

    private Key getSigningKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public TokenDto generateTokenDto(String userId) {
        String accessToken = generateAccessToken(userId);
        String refreshToken = generateRefreshToken(userId);
        return new TokenDto(accessToken, refreshToken);
    }

    public String generateAccessToken(String userId) {
        return generateToken(userId, ACCESS_TOKEN_EXPIRE_TIME);
    }

    public String generateRefreshToken(String userId) {
        return generateToken(userId, REFRESH_TOKEN_EXPIRE_TIME);
    }

    public String generateToken(String userId, int expireTime) {
        Claims claims = Jwts.claims().setSubject(userId);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(getSigningKey(secretKey), SignatureAlgorithm.HS256)
                .compact();
    }

    public Boolean verifyToken(String token) {
        Jwts.parserBuilder().setSigningKey(getSigningKey(secretKey)).build().parseClaimsJws(token);
        return true;
    }

    public Boolean verifyRefreshToken(String token) {
        try {
            if (verifyToken(token) && redisTemplate.opsForValue().get(getUsername(token)).equals(token)) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    private void storeRefreshToken(String userId, String token) {
        redisTemplate.opsForValue().set(userId, token);
        redisTemplate.expire(userId, REFRESH_TOKEN_EXPIRE_TIME, TimeUnit.SECONDS);
    }

    public TokenDto issueToken(String userId) {
        TokenDto tokenDto = generateTokenDto(userId);
        storeRefreshToken(userId, tokenDto.getRefreshToken());
        return tokenDto;
    }

    public String issueRefreshToken(String userId) {
        String refreshToken = generateRefreshToken(userId);
        storeRefreshToken(userId, refreshToken);
        return refreshToken;
    }

    public Authentication getAuthentication(String token) {
        String userPrincipal = getClaims(token).getSubject();

        UserDetails userDetails = customUserDetailService.loadUserByUsername(userPrincipal);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey(secretKey))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if ((bearerToken != null) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public void deleteRefreshToken(String userId) {
        if (redisTemplate.opsForValue().get(userId) != null) {
            redisTemplate.delete(userId);
        }
    }

    public Date getExpiredTime(String token) {
        return getClaims(token).getExpiration();
    }

    public String getUsername(String token) {
        return getClaims(token).getSubject();
    }

    public void setBlackList(String token) {
        redisTemplate.opsForValue().set(token, "logout");
        redisTemplate.expire(token, getExpiredTime(token).getTime(), TimeUnit.MILLISECONDS);
    }
}
