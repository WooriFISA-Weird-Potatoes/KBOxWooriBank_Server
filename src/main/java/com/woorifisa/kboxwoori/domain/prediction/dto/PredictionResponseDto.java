package com.woorifisa.kboxwoori.domain.prediction.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PredictionResponseDto {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean participated;

    private Boolean isEnded;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> predictions;
}
