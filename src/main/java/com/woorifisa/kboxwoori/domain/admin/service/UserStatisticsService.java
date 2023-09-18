package com.woorifisa.kboxwoori.domain.admin.service;

import com.woorifisa.kboxwoori.domain.admin.dto.PredictionResponseDto;
import com.woorifisa.kboxwoori.domain.admin.dto.QuizResponseDto;
import com.woorifisa.kboxwoori.domain.admin.dto.UserResponseDto;
import com.woorifisa.kboxwoori.domain.admin.entity.UserStatistics;
import com.woorifisa.kboxwoori.domain.admin.repository.UserStatisticsRepository;
import com.woorifisa.kboxwoori.domain.event.entity.Event;
import com.woorifisa.kboxwoori.domain.event.repository.EventRepository;
import com.woorifisa.kboxwoori.domain.quiz.entity.Quiz;
import com.woorifisa.kboxwoori.domain.quiz.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserStatisticsService {
    private final UserStatisticsRepository userStatisticsRepository;
    private final QuizRepository quizRepository;
    private final EventRepository eventRepository;

    public UserStatistics saveUserStatistics(UserStatistics userStatistics) {
        return userStatisticsRepository.save(userStatistics);
    }

    public List<UserStatistics> getUserStatisticsForLastNDays(int days) {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(days - 1);
        return userStatisticsRepository.findByCreatedAtBetween(startDate, endDate);
    }

    public List<UserResponseDto> getUserStatisticsForLast7Days() {
        List<UserStatistics> userStatisticsList = getUserStatisticsForLastNDays(7);

        // UserStatistics 객체를 UserResponseDto로 변환
        return userStatisticsList.stream()
                .map(userStatistics -> new UserResponseDto(userStatistics.getTotalUsers(), userStatistics.getCreatedAt()))
                .collect(Collectors.toList());
    }

    public List<QuizResponseDto> getQuizStatisticsForLast7Days() {
        List<UserStatistics> userStatisticsList = getUserStatisticsForLastNDays(7);

        return userStatisticsList.stream()
                .map(userStatistics -> new QuizResponseDto(userStatistics.getQuizParticipants(), userStatistics.getCreatedAt()))
                .collect(Collectors.toList());
    }

    public List<PredictionResponseDto> getPredictionStatisticsForLast7Days() {
        List<UserStatistics> userStatisticsList = getUserStatisticsForLastNDays(7);
        return userStatisticsList.stream()
                .map(userStatistics -> new PredictionResponseDto(userStatistics.getPredictionParticipants(), userStatistics.getCreatedAt()))
                .collect(Collectors.toList());
    }

    public List<UserResponseDto> getUserStatisticsForLast30Days() {
        List<UserStatistics> userStatisticsList = getUserStatisticsForLastNDays(30);

        // UserStatistics 객체를 UserResponseDto로 변환
        return userStatisticsList.stream()
                .map(userStatistics -> new UserResponseDto(userStatistics.getTotalUsers(), userStatistics.getCreatedAt()))
                .collect(Collectors.toList());
    }

    public List<QuizResponseDto> getQuizStatisticsForLast30Days() {
        List<UserStatistics> userStatisticsList = getUserStatisticsForLastNDays(30);
        // UserStatistics 객체를 UserResponseDto로 변환
        return userStatisticsList.stream()
                .map(userStatistics -> new QuizResponseDto(userStatistics.getQuizParticipants(), userStatistics.getCreatedAt()))
                .collect(Collectors.toList());
    }

    public List<PredictionResponseDto> getPredictionStatisticsForLast30Days() {
        List<UserStatistics> userStatisticsList = getUserStatisticsForLastNDays(30);
        // UserStatistics 객체를 UserResponseDto로 변환
        return userStatisticsList.stream()
                .map(userStatistics -> new PredictionResponseDto(userStatistics.getPredictionParticipants(), userStatistics.getCreatedAt()))
                .collect(Collectors.toList());
    }

    public List<Quiz> processQuizFileUpload(MultipartFile file) throws IOException {
        List<Quiz> quizzes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                //파일 그 줄 파싱하고 엔티티 생성 저장
                Quiz quiz = parseQuizLineAndCreateEntity(line);
                quizRepository.save(quiz);
                quizzes.add(quiz);
            }
        }
        return quizzes;
    }


    public List<Event> processEventFileUpload(MultipartFile file) throws IOException {
        List<Event> eventList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {

                Event event = parseProductLineAndCreateEntity(line);
                eventRepository.save(event);
                eventList.add(event);
            }
        }
        return eventList;
    }

    private Quiz parseQuizLineAndCreateEntity(String line) {
        String[] parts = line.split(",");

        String question = parts[0];
        char answer = parts[1].charAt(0);
        String choice1 = parts[2]; // 선착순 정보 (경품(?))
        String choice2 = parts[3];
        String choice3 = parts[4];
        String choice4 = parts[5];
        LocalDate date = LocalDate.parse(parts[6]);

        return Quiz.builder()
                .question(question)
                .answer(answer)
                .choice1(choice1)
                .choice2(choice2)
                .choice3(choice3)
                .choice4(choice4)
                .createdAt(date)
                .build();

    }

    private Event parseProductLineAndCreateEntity(String line) {
        String[] parts = line.split(",");
        String prize = parts[0];
        Integer winnerLimit = Integer.valueOf(parts[1]);
        LocalDate startDate = LocalDate.parse(parts[2]);
        LocalDate endDate = LocalDate.parse(parts[3]);
        Boolean isEnded = Boolean.valueOf(parts[4]);


        return Event.builder()
                .prize(prize)
                .winnerLimit(winnerLimit)
                .startDate(startDate.atStartOfDay())
                .endDate(endDate.atStartOfDay())
                .isEnded(isEnded)
                .build();
    }


}
