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

    private User getUserByUserId(String userId) {
        return userRepository.findPointByUserId(userId)
                .orElseThrow(() -> AccountNotFoundException.EXCEPTION);
    }

    @Transactional
    public PointHistoryDto getPointHistory(String userId){
        User user = getUserByUserId(userId);
        List<Point> pointHistory = pointRepository.findPointByUserId(userId);

        PointHistoryDto pointHistoryDTO = new PointHistoryDto();
        pointHistoryDTO.setPoint(user.getPoint());
        pointHistoryDTO.setPointList(pointHistory);
        return pointHistoryDTO;

    }

    @Transactional
    public UserPointResponseDto getUserPoint(String userId){
        User user = getUserByUserId(userId);
        UserPointResponseDto userPointResponseDTO = new UserPointResponseDto();
        userPointResponseDTO.setPoint(user.getPoint());
        return userPointResponseDTO;
    }

    @Transactional
    public void usePoint(String userId, UserPointResponseDto point){
        User user = getUserByUserId(userId);
        int requestedPoints = point.getPoint();

        if(user.getPoint() < requestedPoints){
            throw InsufficientPointsException.EXCEPTION;
        }

        PointUseDto pointUseDto = new PointUseDto();
        pointUseDto.setUser(user);
        pointUseDto.setStatusCode(PointStatus.USE);
        pointUseDto.setPoint(point.getPoint());
        pointUseDto.setCreatedAt(LocalDate.now());
        pointRepository.save(pointUseDto.toEntity());


        int calculatedPoints = user.getPoint() - requestedPoints;
        user.updateUserPoint(calculatedPoints);
        userRepository.save(user);

    }

}
