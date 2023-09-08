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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class PointService {

   private final PointRepository pointRepository;
   private final UserRepository userRepository;

    private User getUserByUserId(String userId) {
        return userRepository.findPointByUserId(userId)
                .orElseThrow(() -> AccountNotFoundException.EXCEPTION);
    }

    public PointHistoryDto getPointHistory(String userId){
        User user = getUserByUserId(userId);
        List<Point> pointHistory = pointRepository.findPointByUserId(userId);

        PointHistoryDto pointHistoryDTO = new PointHistoryDto();
        pointHistoryDTO.setPoint(user.getPoint());
        pointHistoryDTO.setPointList(pointHistory);
        return pointHistoryDTO;

    }

    public UserPointResponseDto getUserPoint(String userId){
        User user = getUserByUserId(userId);
        UserPointResponseDto userPointResponseDTO = new UserPointResponseDto();
        userPointResponseDTO.setPoint(user.getPoint());
        return userPointResponseDTO;
    }

    @Transactional
    public void usePoint(String userId, UserPointResponseDto pointDto){
        User user = getUserByUserId(userId);
        int requestedPoints = pointDto.getPoint();

        if(user.getPoint() < requestedPoints){
            throw InsufficientPointsException.EXCEPTION;
        }

        Point point = Point.builder()
                .user(user)
                .statusCode(PointStatus.USE)
                .point(requestedPoints)
                .createdAt(LocalDateTime.now())
                .build();
        pointRepository.save(point);
        user.updateUserPoint(-requestedPoints);
    }

    @Transactional
    public void savePoint(String userId, int pointEarned) {
        User user = getUserByUserId(userId);

        Point point = Point.builder()
                .user(user)
                .statusCode(PointStatus.SAVE)
                .point(pointEarned)
                .createdAt(LocalDateTime.now())
                .build();
        pointRepository.save(point);
        user.updateUserPoint(pointEarned);
    }

}
