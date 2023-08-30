package com.woorifisa.kboxwoori.domain.admin.dto;

import lombok.Getter;

import java.time.LocalDate;

public class QuizResponseDto {

    private Integer quizParticipants;
    @Getter
    private LocalDate createdAt;

    public QuizResponseDto(Integer quizParticipants, LocalDate createdAt) {
        this.quizParticipants = quizParticipants;
        this.createdAt = createdAt;
    }

    public Integer getQuizParticipants() {
        return quizParticipants;
    }

}

