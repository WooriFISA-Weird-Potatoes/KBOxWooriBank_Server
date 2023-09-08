package com.woorifisa.kboxwoori.domain.prediction.controller;

import com.woorifisa.kboxwoori.domain.prediction.dto.PredictionRequestDto;
import com.woorifisa.kboxwoori.domain.prediction.dto.PredictionResponseDto;
import com.woorifisa.kboxwoori.domain.prediction.exception.InvalidPredictionParticipationTimeException;
import com.woorifisa.kboxwoori.domain.prediction.service.PredictionService;
import com.woorifisa.kboxwoori.global.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.woorifisa.kboxwoori.global.util.AuthenticationUtil.getCurrentUserId;

@RestController
@RequestMapping("/api/predictions")
@RequiredArgsConstructor
public class PredictionController {

    private final PredictionService predictionService;

    @GetMapping
    public ResponseDto<PredictionResponseDto> getPrediction() {
        PredictionResponseDto prediction = predictionService.getPrediction(getCurrentUserId());
        prediction.setIsEnded(predictionService.isPredictionEnded());

        return ResponseDto.success(prediction);
    }

    @PostMapping
    public ResponseDto savePrediction(@Valid @RequestBody PredictionRequestDto predictionRequestDto) {
        if (predictionService.isPredictionEnded()) {
            throw InvalidPredictionParticipationTimeException.EXCEPTION;
        }

        predictionService.savePrediction(getCurrentUserId(), predictionRequestDto);
        return ResponseDto.success();
    }
}
