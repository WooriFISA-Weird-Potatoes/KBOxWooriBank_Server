package com.woorifisa.kboxwoori.domain.user.service;

import com.woorifisa.kboxwoori.domain.event.exception.WooriLinkRequiredException;
import com.woorifisa.kboxwoori.domain.point.repository.PointRepository;
import com.woorifisa.kboxwoori.domain.prediction.entity.PredictionHistory;
import com.woorifisa.kboxwoori.domain.prediction.repository.PredictionHistoryRepository;
import com.woorifisa.kboxwoori.domain.user.dto.UserInfoResponseDto;
import com.woorifisa.kboxwoori.domain.user.dto.UserDto;
import com.woorifisa.kboxwoori.domain.user.dto.UserPageResponseDto;
import com.woorifisa.kboxwoori.domain.user.dto.UserSessionDto;
import com.woorifisa.kboxwoori.domain.user.entity.User;
import com.woorifisa.kboxwoori.domain.user.exception.AccountNotFoundException;
import com.woorifisa.kboxwoori.domain.user.repository.UserRepository;
import com.woorifisa.kboxwoori.global.config.security.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PointRepository pointRepository;
    private final PredictionHistoryRepository predictionHistoryRepository;
    private final BCryptPasswordEncoder encoder;
    private final HttpSession session;

    private User getUserByUserId(String userId) {
        return userRepository.findPointByUserId(userId)
                .orElseThrow(() -> AccountNotFoundException.EXCEPTION);
    }

    @Transactional
    public Long join(UserDto userDto) {
        userDto.setPassword(encoder.encode(userDto.getPassword()));
        return userRepository.save(userDto.toEntity()).getId();
    }

    @Transactional
    public boolean existsByUserId(String userId){
        return  userRepository.existsByUserId(userId);
    }

    @Transactional
    public boolean deleteUser(String userId) {
        User user = getUserByUserId(userId);
        pointRepository.deletePointByUserId(user.getId());
        userRepository.deleteByUserId(user.getUserId());
        return true;
    }

    @Transactional
    public UserInfoResponseDto findUser(String userId) {
        User user = getUserByUserId(userId);
        return new UserInfoResponseDto(user);
    }

    @Transactional
    public UserInfoResponseDto updateUserInfo(PrincipalDetails pdetail, UserInfoResponseDto responseDTO){
        User user = getUserByUserId(pdetail.getUsername());
        responseDTO.setPassword(encoder.encode(responseDTO.getPassword()));
        user.updateUser(responseDTO);
        session.setAttribute("user", new UserSessionDto(user));
        pdetail.setUser(user);
        return responseDTO;
    }

    @Transactional
    public UserPageResponseDto myPageUserInfo(String userId){
        User user = getUserByUserId(userId);
        List<PredictionHistory> totalList = predictionHistoryRepository.findByUserId(userId).orElseThrow(() -> AccountNotFoundException.EXCEPTION);
        List<PredictionHistory> correctList = predictionHistoryRepository.findByUserIdAndIsCorrect(userId).orElseThrow(() -> AccountNotFoundException.EXCEPTION);

        int totalPredictionCount = totalList.size();
        int correctPredictionCount = correctList.size();
        double correctRate = totalPredictionCount > 0 ? ((double) correctPredictionCount / totalPredictionCount) * 100 : 0;

        UserPageResponseDto userPageResponseDto = new UserPageResponseDto(user);
        userPageResponseDto.setPredictedResult((int) correctRate);

        return userPageResponseDto;
    }

    public Boolean IsWooriLinked(String userId) {
        User user = getUserByUserId(userId);
        if (!user.getWooriLinked()) {
            throw WooriLinkRequiredException.EXCEPTION;
        }
        return true;
    }

}