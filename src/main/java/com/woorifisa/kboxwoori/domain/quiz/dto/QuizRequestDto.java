package com.woorifisa.kboxwoori.domain.quiz.dto;

import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuizRequestDto {
    @NonNull
    private Long quizId;
    @NonNull
    private char choice;
}
