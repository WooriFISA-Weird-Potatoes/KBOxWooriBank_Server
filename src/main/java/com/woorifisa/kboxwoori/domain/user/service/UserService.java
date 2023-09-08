package com.woorifisa.kboxwoori.domain.user.service;

import com.woorifisa.kboxwoori.domain.point.repository.PointRepository;
import com.woorifisa.kboxwoori.domain.prediction.entity.PredictionHistory;
import com.woorifisa.kboxwoori.domain.prediction.exception.ParticipationRecordNotFoundException;
import com.woorifisa.kboxwoori.domain.prediction.repository.PredictionHistoryRepository;
import com.woorifisa.kboxwoori.domain.user.dto.*;
import com.woorifisa.kboxwoori.domain.user.entity.User;
import com.woorifisa.kboxwoori.domain.user.exception.AccountNotFoundException;
import com.woorifisa.kboxwoori.domain.user.exception.InvalidUserIdPwException;
import com.woorifisa.kboxwoori.domain.user.repository.UserRepository;
import com.woorifisa.kboxwoori.global.exception.security.InvalidRefreshTokenException;
import com.woorifisa.kboxwoori.global.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PointRepository pointRepository;
    private final PredictionHistoryRepository predictionHistoryRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private User getUserByUserId(String userId) {
        return userRepository.findPointByUserId(userId)
                .orElseThrow(() -> AccountNotFoundException.EXCEPTION);
    }

    @Transactional
    public Long join(UserDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return userRepository.save(userDto.toEntity()).getId();
    }

    public boolean existsByUserId(String userId){
        return userRepository.existsByUserId(userId);
    }

    @Transactional
    public TokenDto login(UserLoginRequestDto userLoginRequestDto) {
        try {
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userLoginRequestDto.getUserId(),
                            userLoginRequestDto.getPassword()
                    )
            );

            return jwtUtil.issueToken(authentication.getName());
        } catch (BadCredentialsException e) {
            throw InvalidUserIdPwException.EXCEPTION;
        }
    }

    @Transactional
    public void logout(String userId, String token) {
        jwtUtil.deleteRefreshToken(userId);
        jwtUtil.setBlackList(token);
    }

    @Transactional
    public void deleteUser(String userId) {
        User user = getUserByUserId(userId);
        pointRepository.deletePointByUserId(user.getId());
        userRepository.deleteByUserId(user.getUserId());
        jwtUtil.deleteRefreshToken(userId);
    }

    public UserInfoResponseDto findUser(String userId) {
        User user = getUserByUserId(userId);
        return new UserInfoResponseDto(user);
    }

    @Transactional
    public void updateUserInfo(String userId, UserInfoRequestDto requestDto){
        User user = getUserByUserId(userId);
        requestDto.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        user.updateUser(requestDto);
    }

    public UserPageResponseDto myPageUserInfo(String userId){
        User user = getUserByUserId(userId);
        List<PredictionHistory> totalList = predictionHistoryRepository.findByUserId(userId).orElseThrow(() -> AccountNotFoundException.EXCEPTION);
        List<PredictionHistory> correctList = predictionHistoryRepository.findByUserIdAndIsCorrect(userId).orElseThrow(() -> ParticipationRecordNotFoundException.EXCEPTION);

        int totalPredictionCount = totalList.size();
        int correctPredictionCount = correctList.size();
        double correctRate = totalPredictionCount > 0 ? ((double) correctPredictionCount / totalPredictionCount) * 100 : 0;

        UserPageResponseDto userPageResponseDto = new UserPageResponseDto(user);
        userPageResponseDto.setPredictedResult((int) correctRate);

        return userPageResponseDto;
    }

    public UserAddrResponseDto getAddress(String userId) {
        User user = getUserByUserId(userId);
        return new UserAddrResponseDto(user.getAddr());
    }

    @Transactional
    public TokenDto refreshToken(HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        if (token == null || !jwtUtil.verifyRefreshToken(token)) {
            throw InvalidRefreshTokenException.EXCEPTION;
        }

        String accessToken = jwtUtil.generateAccessToken(jwtUtil.getUsername(token));
        return new TokenDto(accessToken, token);
    }
}