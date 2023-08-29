package com.woorifisa.kboxwoori.domain.user.service;

import com.woorifisa.kboxwoori.domain.event.exception.WooriLinkRequiredException;
import com.woorifisa.kboxwoori.domain.point.repository.PointRepository;
import com.woorifisa.kboxwoori.domain.prediction.entity.PredictionHistory;
import com.woorifisa.kboxwoori.domain.prediction.repository.PredictionHistoryRepository;
import com.woorifisa.kboxwoori.domain.user.dto.UserInfoResponseDto;
import com.woorifisa.kboxwoori.domain.user.dto.UserDto;
import com.woorifisa.kboxwoori.domain.user.dto.UserPageResponseDto;
import com.woorifisa.kboxwoori.domain.user.entity.User;
import com.woorifisa.kboxwoori.domain.user.exception.AccountNotFoundException;
import com.woorifisa.kboxwoori.domain.user.repository.UserRepository;
import com.woorifisa.kboxwoori.global.config.security.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PointRepository pointRepository;
    private final PredictionHistoryRepository predictionHistoryRepository;
    private final BCryptPasswordEncoder encoder;

    @Transactional
    public Long join(UserDto userDto) {
        userDto.setPassword(encoder.encode(userDto.getPassword()));
        return userRepository.save(userDto.toEntity()).getId();
    }

    @Transactional
    public boolean deleteUser(String userId) {
        User user = userRepository.findByUserId(userId).orElseThrow(() -> AccountNotFoundException.EXCEPTION);
        pointRepository.deletePointByUserId(user.getId());
        userRepository.deleteByUserId(user.getUserId());
        return true;
    }

    @Transactional
    public UserInfoResponseDto findUser(String userId) {
        User user = userRepository.findByUserId(userId).orElseThrow(() -> AccountNotFoundException.EXCEPTION);
        UserInfoResponseDto updateUserResponseDTO = new UserInfoResponseDto(user);
        return updateUserResponseDTO;
    }

    @Transactional
    public UserInfoResponseDto updateUserInfo(PrincipalDetails pdetail, UserInfoResponseDto responseDTO){
        User user = userRepository.findByUserId(pdetail.getUsername()).orElseThrow(() -> AccountNotFoundException.EXCEPTION);
        responseDTO.setPassword(encoder.encode(responseDTO.getPassword()));
        user.updateUser(responseDTO);
        pdetail.setUser(user);
        return responseDTO;
    }

    @Transactional
    public UserPageResponseDto myPageUserInfo(String userId){
        User user = userRepository.findByUserId(userId).orElseThrow(() -> AccountNotFoundException.EXCEPTION);
        List<PredictionHistory> totalList = predictionHistoryRepository.findByUserId(userId).orElseThrow(() -> AccountNotFoundException.EXCEPTION);
        List<PredictionHistory> correctList = predictionHistoryRepository.findByUserIdAndIsCorrect(userId).orElseThrow(() -> AccountNotFoundException.EXCEPTION);

        int totalPredictionCount = totalList.size();
        int correctPredictionCount = correctList.size();
        double correctRate = ((double) correctPredictionCount/totalPredictionCount) * 100;

        UserPageResponseDto userPageResponseDto = new UserPageResponseDto(user);

        if(totalPredictionCount == 0){
            userPageResponseDto.setPredictedResult(0);
        }
        userPageResponseDto.setPredictedResult((int)correctRate);

        return userPageResponseDto;
    }

    public Boolean IsWooriLinked(String userId) {
        User user = userRepository.findByUserId(userId).orElseThrow(() -> AccountNotFoundException.EXCEPTION);
        if (!user.getWooriLinked()) {
            throw WooriLinkRequiredException.EXCEPTION;
        }
        return true;
    }

}