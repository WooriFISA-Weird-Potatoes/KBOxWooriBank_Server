package com.woorifisa.kboxwoori.domain.quiz.dto;

import com.woorifisa.kboxwoori.domain.quiz.entity.Quiz;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class QuizResponseDto {
    private Long id;
    private String question;
    private String choice1;
    private String choice2;
    private String choice3;
    private String choice4;
    private LocalDate createdAt;
    private char answer;

    public QuizResponseDto(Quiz quiz) {
        this.id = quiz.getId();
        this.question = quiz.getQuestion();
        this.choice1 = quiz.getChoice1();
        this.choice2 = quiz.getChoice2();
        this.choice3 = quiz.getChoice3();
        this.choice4 = quiz.getChoice4();
        this.createdAt = quiz.getCreatedAt();
        this.answer = quiz.getAnswer();
    }

}
