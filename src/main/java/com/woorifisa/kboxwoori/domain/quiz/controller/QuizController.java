package com.woorifisa.kboxwoori.domain.quiz.controller;

import com.woorifisa.kboxwoori.domain.quiz.dto.QuizRequestDto;
import com.woorifisa.kboxwoori.domain.quiz.dto.QuizResponseDto;
import com.woorifisa.kboxwoori.domain.quiz.dto.QuizResultResponseDto;
import com.woorifisa.kboxwoori.domain.quiz.service.QuizParticipantService;
import com.woorifisa.kboxwoori.domain.quiz.service.QuizService;
import com.woorifisa.kboxwoori.domain.user.exception.NotAuthenticatedAccountException;
import com.woorifisa.kboxwoori.global.config.security.PrincipalDetails;
import com.woorifisa.kboxwoori.global.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/quizzes")
public class QuizController {

    private final QuizService quizService;
    private final QuizParticipantService quizParticipantService;

    @GetMapping
    public ResponseDto<QuizResponseDto> getCurrentQuiz(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        QuizResponseDto quiz = quizService.getCurrentQuiz();
        if (principalDetails != null) {
            quiz.setHasParticipated(quizParticipantService.isUserParticipated(principalDetails.getUsername()));
        }
        return ResponseDto.success(quiz);
    }

    @PostMapping
    public ResponseDto<QuizResultResponseDto> submitAnswer(@RequestBody @Valid QuizRequestDto quizRequestDTO,
                                                           @AuthenticationPrincipal PrincipalDetails principalDetails) {
        if (principalDetails == null) {
            throw NotAuthenticatedAccountException.EXCEPTION;
        }

        return ResponseDto.success(quizService.submitAnswer(quizRequestDTO, principalDetails.getUsername()));
    }
}
