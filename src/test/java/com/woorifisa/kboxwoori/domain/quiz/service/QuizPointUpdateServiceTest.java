package com.woorifisa.kboxwoori.domain.quiz.service;

import com.woorifisa.kboxwoori.domain.point.entity.Point;
import com.woorifisa.kboxwoori.domain.point.entity.PointStatus;
import com.woorifisa.kboxwoori.domain.point.repository.PointRepository;
import com.woorifisa.kboxwoori.domain.user.entity.Club;
import com.woorifisa.kboxwoori.domain.user.entity.Gender;
import com.woorifisa.kboxwoori.domain.user.entity.User;
import com.woorifisa.kboxwoori.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class QuizPointUpdateServiceTest {

    @Autowired
    private QuizPointUpdateService quizPointUpdateService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PointRepository pointRepository;

    private  User user;

    @BeforeEach
    void beforeEach() {
         user = User.builder()
                .userId("userid")
                .gender(Gender.M)
                .admin(false)
                .birth(LocalDate.now())
                .phone("000")
                .point(46)
                .password("password")
                .svcAgmt(true)
                .name("name")
                .wooriLinked(true)
                .club(Club.NC)
                .addr("addr")
                .infoAgmt(true)
                .build();
        userRepository.save(user);
    }
        @Test
        public void testUpdateUserPoint () {
            int pointEarned = 3;
            //메서드 호출
            quizPointUpdateService.updateUserPoint(user,pointEarned);

            // 업데이트 후 사용자 포인트 확인
            User updateUser = userRepository.findByUserId("userid").orElse(null);
            assertNotNull(updateUser);
            assertEquals(49, updateUser.getPoint());

            // Point 엔티티 객체 생성
            Point point = Point.builder()
                    .user(updateUser)
                    .statusCode(PointStatus.SAVE)// 포인트 상태 설정
                    .point(pointEarned)
                    .createdAt(LocalDate.now())
                    .build();

            // 사용자 엔터티와 포인트 엔터티
            pointRepository.save(point);

            //포인트 이력 확인
            Point savedPoint = pointRepository.findById(point.getId()).orElse(null);
            assertNotNull(point);
            assertEquals(PointStatus.SAVE, point.getStatusCode());
            assertEquals(pointEarned, point.getPoint());
            assertNotNull(point.getCreatedAt());
        }

    }