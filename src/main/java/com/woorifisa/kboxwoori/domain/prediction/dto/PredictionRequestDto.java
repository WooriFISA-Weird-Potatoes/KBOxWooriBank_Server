package com.woorifisa.kboxwoori.domain.prediction.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PredictionRequestDto {

    @NotNull(message = "모든 경기 예측 후 참여 가능합니다.")
    private List<String> predictions;
}
