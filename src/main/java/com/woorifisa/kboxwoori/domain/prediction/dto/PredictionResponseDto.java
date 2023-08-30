package com.woorifisa.kboxwoori.domain.prediction.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class PredictionResponseDto {
    private Boolean participated;
    private Boolean isEnded = false;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> predictions;
}
