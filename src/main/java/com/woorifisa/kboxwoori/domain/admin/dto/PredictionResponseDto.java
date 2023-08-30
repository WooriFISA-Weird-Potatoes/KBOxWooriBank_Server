package com.woorifisa.kboxwoori.domain.admin.dto;

import lombok.Getter;

import java.time.LocalDate;
@Getter
public class PredictionResponseDto {

    private Integer predictionPariticipnats;
    private LocalDate createdAt;

    public PredictionResponseDto(Integer predictionPariticipnats, LocalDate createdAt){
        this.predictionPariticipnats = predictionPariticipnats;
        this.createdAt = createdAt;
    }

}
