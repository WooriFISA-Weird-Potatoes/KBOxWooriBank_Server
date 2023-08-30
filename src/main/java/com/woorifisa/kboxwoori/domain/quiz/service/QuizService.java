package com.woorifisa.kboxwoori.domain.quiz.service;

import com.woorifisa.kboxwoori.domain.quiz.dto.QuizRequestDto;
import com.woorifisa.kboxwoori.domain.quiz.dto.QuizResponseDto;
import com.woorifisa.kboxwoori.domain.quiz.dto.QuizResultResponseDto;
import com.woorifisa.kboxwoori.domain.quiz.entity.Quiz;
import com.woorifisa.kboxwoori.domain.quiz.exception.*;
import com.woorifisa.kboxwoori.domain.quiz.repository.QuizRepository;
import com.woorifisa.kboxwoori.domain.user.entity.User;
import com.woorifisa.kboxwoori.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;
    private final UserRepository userRepository;
    private final QuizParticipantService quizParticipantService;

    private static final int CORRECT_ANSWER_POINTS = 3;

    public QuizResponseDto getCurrentQuiz() {
        //현재 날짜에 맞는 날짜
        LocalDate currentDate = LocalDate.now();
        //현재 날짜에 해당하는 퀴즈 가져옴
        List <Quiz> quizzes = quizRepository.findByCreatedAt(currentDate);

        if (quizzes.isEmpty()) {
            throw QuizNotFoundException.EXCEPTION;
        }

        QuizResponseDto quizResponseDTO = new QuizResponseDto(quizzes.get(0));
        return quizResponseDTO;
    }

    public QuizResultResponseDto submitAnswer(QuizRequestDto quizRequestDTO, String userId) {
        LocalDate currentDate = LocalDate.now();

        //주어진 quizId 퀴즈 가져옴
        Quiz quiz = quizRepository.findById(quizRequestDTO.getQuizId()).orElseThrow(() -> QuizNotFoundException.EXCEPTION);

        if (!quiz.getCreatedAt().equals(currentDate)) {
            throw QuizEndedException.EXCEPTION;
        }

        User user = userRepository.findByUserId(userId).orElseThrow(() -> AccountNotFoundException.EXCEPTION);

        //사용자 참여 여부 redis 통해 확인
        if (quizParticipantService.isUserParticipated(user.getUserId())) {
            throw QuizDuplicatePartipation.EXCEPTION;
        }

        //정답 가져옴
        char correctAnswer = quiz.getAnswer();

        if (quizRequestDTO.getChoice() != correctAnswer) {
            return new QuizResultResponseDto(false);
        }
        //정답일 경우 처리
        quizParticipantService.saveUserParticipation(user.getUserId());
        //TODO: 포인트 적립
        return new QuizResultResponseDto(true);
    }

}

