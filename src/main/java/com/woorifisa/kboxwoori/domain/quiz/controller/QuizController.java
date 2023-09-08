package com.woorifisa.kboxwoori.domain.quiz.controller;

import com.woorifisa.kboxwoori.domain.quiz.dto.QuizParticipationResponseDto;
import com.woorifisa.kboxwoori.domain.quiz.dto.QuizRequestDto;
import com.woorifisa.kboxwoori.domain.quiz.dto.QuizResponseDto;
import com.woorifisa.kboxwoori.domain.quiz.dto.QuizResultResponseDto;
import com.woorifisa.kboxwoori.domain.quiz.service.QuizService;
import com.woorifisa.kboxwoori.global.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.woorifisa.kboxwoori.global.util.AuthenticationUtil.getCurrentUserId;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/quizzes")
public class QuizController {

    private final QuizService quizService;

    @GetMapping
    public ResponseDto<QuizResponseDto> getCurrentQuiz() {
        QuizResponseDto quiz = quizService.getCurrentQuiz();
        return ResponseDto.success(quiz);
    }

    @GetMapping("/{quizId}")
    public ResponseDto<QuizParticipationResponseDto> getCurrentQuizParticipation(@PathVariable String quizId) {
        QuizParticipationResponseDto responseDto = new QuizParticipationResponseDto(quizService.isUserParticipated(getCurrentUserId()));
        return ResponseDto.success(responseDto);
    }

    @PostMapping("/{quizId}")
    public ResponseDto<QuizResultResponseDto> submitAnswer(@RequestBody @Valid QuizRequestDto quizRequestDTO) {
        System.out.println("quizRequestDTO = " + quizRequestDTO);
        return ResponseDto.success(quizService.submitAnswer(quizRequestDTO, getCurrentUserId()));
    }
}
