package com.woorifisa.kboxwoori.domain.admin.service;

import com.woorifisa.kboxwoori.domain.admin.dto.PredictionResponseDto;
import com.woorifisa.kboxwoori.domain.admin.dto.QuizResponseDto;
import com.woorifisa.kboxwoori.domain.admin.dto.UserResponseDto;
import com.woorifisa.kboxwoori.domain.admin.entity.UserStatistics;
import com.woorifisa.kboxwoori.domain.admin.repository.UserStatisticsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserStatisticsServiceTest {

    @Autowired
    private UserStatisticsService userStatisticsService;

    @Autowired
    private UserStatisticsRepository userStatisticsRepository;


    @BeforeEach
    public void setUpForLast30Days() {
        // 테스트용 30일 데이터 저장
        LocalDate currentDate = LocalDate.now();
        for (int i = 0; i < 30; i++) {
            UserStatistics userStatistics = UserStatistics.builder()
                    .totalUsers(100 + i)
                    .predictionParticipants(50 + i)
                    .quizParticipant(30 + i)
                    .createdAt(currentDate.minusDays(i))
                    .build();
            userStatisticsRepository.save(userStatistics);
        }
    }


    @Test
    public void testGetUserStatisticsForLast7Days() {
        //7일 테스트
        List<UserResponseDto> result = userStatisticsService.getUserStatisticsForLast7Days();
        result.forEach(dto -> {
            System.out.println("Total Users: " + dto.getTotalUsers());
            System.out.println("Created At: " + dto.getCreatedAt());
        });
        assertEquals(7, result.size());


        List<QuizResponseDto> quizResult = userStatisticsService.getQuizStatisticsForLast7Days();
        quizResult.forEach(dto -> {
            System.out.println("Quiz Participants: " + dto.getQuizParticipants());
            System.out.println("Created At: " + dto.getCreatedAt());
        });
        assertEquals(7, quizResult.size());

        List<PredictionResponseDto> predictionResult = userStatisticsService.getPredictionStatisticsForLast7Days();
        predictionResult.forEach(dto -> {
            System.out.println("Prediction Participants: " + dto.getPredictionPariticipnats());
            System.out.println("Created At: " + dto.getCreatedAt());
        });
        assertEquals(7, predictionResult.size());

    }

    @Test
    public void testGetUserStatisticsForLast30Days() {
        //7일 테스트
        List<UserResponseDto> result = userStatisticsService.getUserStatisticsForLast30Days();
        result.forEach(dto -> {
            System.out.println("Total Users: " + dto.getTotalUsers());
            System.out.println("Created At: " + dto.getCreatedAt());
        });
        assertEquals(30, result.size());


        List<QuizResponseDto> quizResult = userStatisticsService.getQuizStatisticsForLast30Days();
        quizResult.forEach(dto -> {
            System.out.println("Quiz Participants: " + dto.getQuizParticipants());
            System.out.println("Created At: " + dto.getCreatedAt());
        });
        assertEquals(30, quizResult.size());

        List<PredictionResponseDto> predictionResult = userStatisticsService.getPredictionStatisticsForLast30Days();
        predictionResult.forEach(dto -> {
            System.out.println("Prediction Participants: " + dto.getPredictionPariticipnats());
            System.out.println("Created At: " + dto.getCreatedAt());
        });
        assertEquals(30, predictionResult.size());
    }

    @Test
    public void testProcessQuizFileUpload() throws IOException {
        //테스트할 csv 데이터 생성
        String csvData = "퀴즈1,선택지1,선택지2,선택지3,선택지4,2023-08-28\n퀴즈2,선택지1,선택지2,선택지3,선택지4,2023-08-29\n퀴즈3,선택지1,선택지2,선택지3,선택지4,2023-08-30";
        //MultipartFile 객체 생성 및 데이터 설정
        MockMultipartFile multipartFile = new MockMultipartFile(
                "test.csv",           // 파일 이름
                "test.csv",           // 원본 파일 이름
                "text/plain",         // 파일 타입
                csvData.getBytes()
        );

        //퀴즈 파일 업로드 메서드 호출
        userStatisticsService.processQuizFileUpload(multipartFile);

        //업로드 후 해당 데이터가 db에 있나,..
        List<UserStatistics> uploadedUserStatistics = userStatisticsRepository.findAll();
        assertNotNull(uploadedUserStatistics);
        assertEquals(30, uploadedUserStatistics.size());
    }

    @Test
    public void testProcessEventFileUpload() throws IOException {
        // 상품 csv
        String csvData = "이벤트1,100,2023-08-30,2023-09-15,true\n이벤트2,50,2023-09-01,2023-09-10,false\n이벤트3,200,2023-09-05,2023-09-20,true";
        //
        MockMultipartFile multipartFile = new MockMultipartFile(
                "evnet.csv",        // 파일 이름
                "event.csv",        // 원본 파일 이름
                "text/plain",         // 파일 타입
                csvData.getBytes()     // CSV 데이터
        );
        userStatisticsService.processEventFileUpload(multipartFile);

        //업로드 후 있는지 확인
        List<UserStatistics> uploadedUserStatistics = userStatisticsRepository.findAll();
        assertNotNull(uploadedUserStatistics);
        assertEquals(30, uploadedUserStatistics.size());
    }

}