package com.woorifisa.kboxwoori.domain.point.service;

import com.woorifisa.kboxwoori.domain.point.dto.PointHistoryDto;
import com.woorifisa.kboxwoori.domain.point.dto.PointUseDto;
import com.woorifisa.kboxwoori.domain.point.dto.UserPointResponseDto;
import com.woorifisa.kboxwoori.domain.point.entity.Point;
import com.woorifisa.kboxwoori.domain.point.entity.PointStatus;
import com.woorifisa.kboxwoori.domain.point.exception.InsufficientPointsException;
import com.woorifisa.kboxwoori.domain.point.repository.PointRepository;
import com.woorifisa.kboxwoori.domain.user.entity.User;
import com.woorifisa.kboxwoori.domain.user.exception.AccountNotFoundException;
import com.woorifisa.kboxwoori.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PointService {

   private final PointRepository pointRepository;
   private final UserRepository userRepository;

    @Transactional
    public PointHistoryDto getPointHistory(String userId){
        List<Point> pointHistory = pointRepository.findPointByUserId(userId);
        PointHistoryDto pointHistoryDTO = new PointHistoryDto();
        User user = userRepository.findPointByUserId(userId).get();
        pointHistoryDTO.setPoint(user.getPoint());
        pointHistoryDTO.setPointList(pointHistory);
        return pointHistoryDTO;

    }

    @Transactional
    public UserPointResponseDto getUserPoint(String userId){
        User user = userRepository.findPointByUserId(userId).get();
        UserPointResponseDto userPointResponseDTO = new UserPointResponseDto();
        userPointResponseDTO.setPoint(user.getPoint());
        return userPointResponseDTO;
    }

    @Transactional
    public void usePoint(String userId, UserPointResponseDto point){
        User user = userRepository.findPointByUserId(userId).orElseThrow(() -> AccountNotFoundException.EXCEPTION);
        PointUseDto pointUseDto = new PointUseDto();
        pointUseDto.setUser(user);
        pointUseDto.setStatusCode(PointStatus.USE);
        pointUseDto.setPoint(point.getPoint());
        pointUseDto.setCreatedAt(LocalDate.now());

        if(user.getPoint() < point.getPoint()){
            throw InsufficientPointsException.EXCEPTION;
        }

        int calculatedPoints = user.getPoint() - point.getPoint();
        user.updateUserPoint(calculatedPoints);
        pointRepository.save(pointUseDto.toEntity());
        userRepository.save(user);

    }

}
