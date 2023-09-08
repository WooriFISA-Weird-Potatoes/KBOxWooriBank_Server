package com.woorifisa.kboxwoori.domain.quiz.service;

import com.woorifisa.kboxwoori.domain.notification.service.NotificationService;
import com.woorifisa.kboxwoori.domain.point.service.PointService;
import com.woorifisa.kboxwoori.domain.quiz.dto.QuizRequestDto;
import com.woorifisa.kboxwoori.domain.quiz.dto.QuizResponseDto;
import com.woorifisa.kboxwoori.domain.quiz.dto.QuizResultResponseDto;
import com.woorifisa.kboxwoori.domain.quiz.entity.Quiz;
import com.woorifisa.kboxwoori.domain.quiz.exception.QuizDuplicatePartipation;
import com.woorifisa.kboxwoori.domain.quiz.exception.QuizEndedException;
import com.woorifisa.kboxwoori.domain.quiz.exception.QuizNotFoundException;
import com.woorifisa.kboxwoori.domain.quiz.repository.QuizRedisRepository;
import com.woorifisa.kboxwoori.domain.quiz.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuizRedisRepository quizRedisRepository;
    private final PointService pointService;
    private final NotificationService notificationService;

    private static final int CORRECT_ANSWER_POINTS = 3;

    public QuizResponseDto getCurrentQuiz() {
        List <Quiz> quizzes = quizRepository.findByCreatedAt(LocalDate.now());

        if (quizzes.isEmpty()) {
            throw QuizNotFoundException.EXCEPTION;
        }

        return new QuizResponseDto(quizzes.get(0));
    }

    public QuizResultResponseDto submitAnswer(QuizRequestDto quizRequestDTO, String userId) {
        Quiz quiz = quizRepository.findById(quizRequestDTO.getQuizId()).orElseThrow(() -> QuizNotFoundException.EXCEPTION);

        if (!quiz.getCreatedAt().equals(LocalDate.now())) {
            throw QuizEndedException.EXCEPTION;
        }

        //사용자 참여 여부 redis 통해 확인
        if (isUserParticipated(userId)) {
            throw QuizDuplicatePartipation.EXCEPTION;
        }

        if (quizRequestDTO.getChoice() != quiz.getAnswer()) {
            return new QuizResultResponseDto(false);
        }

        //정답일 경우 처리
        quizRedisRepository.saveUserParticipation(userId);
        pointService.savePoint(userId, CORRECT_ANSWER_POINTS);
        notificationService.saveQuizNotification(userId, (long) CORRECT_ANSWER_POINTS);

        return new QuizResultResponseDto(true);
    }

    public Boolean isUserParticipated(String userId) {
        return quizRedisRepository.isUserParticipated(userId);
    }

}

