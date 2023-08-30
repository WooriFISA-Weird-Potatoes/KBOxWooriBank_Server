package com.woorifisa.kboxwoori.domain.quiz.service;

import com.woorifisa.kboxwoori.domain.point.entity.Point;
import com.woorifisa.kboxwoori.domain.point.entity.PointStatus;
import com.woorifisa.kboxwoori.domain.point.repository.PointRepository;
import com.woorifisa.kboxwoori.domain.user.entity.User;
import com.woorifisa.kboxwoori.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class QuizPointUpdateService {
    private final PointRepository pointRepository;
    private final UserRepository userRepository;

    @Autowired
    public QuizPointUpdateService(PointRepository pointRepository, UserRepository userRepository) {
        this.pointRepository = pointRepository;
        this.userRepository = userRepository;
    }

    public void updateUserPoint(User user, int pointsEarned) {

        int currentPoint = user.getPoint();
        Integer updatePoint = currentPoint + pointsEarned;

        LocalDate currentDate = LocalDate.now();

        // 업데이트
        user.updateUserPoint(updatePoint);
        userRepository.save(user);

        Point point = Point.builder()
                .user(user)
                .statusCode(PointStatus.SAVE)
                .point(pointsEarned)
                .createdAt(currentDate)
                .build();
        pointRepository.save(point);

    }
}
