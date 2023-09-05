package com.woorifisa.kboxwoori.domain.prediction.controller;

import com.woorifisa.kboxwoori.domain.prediction.dto.PredictionRequestDto;
import com.woorifisa.kboxwoori.domain.prediction.dto.PredictionResponseDto;
import com.woorifisa.kboxwoori.domain.prediction.exception.InvalidPredictionParticipationTimeException;
import com.woorifisa.kboxwoori.domain.prediction.service.PredictionService;
import com.woorifisa.kboxwoori.domain.user.exception.NotAuthenticatedAccountException;
import com.woorifisa.kboxwoori.global.config.security.PrincipalDetails;
import com.woorifisa.kboxwoori.global.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/predictions")
@RequiredArgsConstructor
public class PredictionController {

    private final PredictionService predictionService;

    @GetMapping
    public ResponseDto<PredictionResponseDto> getPrediction(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        PredictionResponseDto prediction = new PredictionResponseDto();

        if (principalDetails != null) {
            prediction = predictionService.getPrediction(principalDetails.getUsername());
        }

        prediction.setIsEnded(predictionService.isPredictionEnded());

        return ResponseDto.success(prediction);
    }

    @PostMapping
    public ResponseDto savePrediction(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                      @Valid @RequestBody PredictionRequestDto predictionRequestDto) {
        if (principalDetails == null) {
            throw NotAuthenticatedAccountException.EXCEPTION;
        }

        if (predictionService.isPredictionEnded()) {
            throw InvalidPredictionParticipationTimeException.EXCEPTION;
        }

        predictionService.savePrediction(principalDetails.getUsername(), predictionRequestDto);
        return ResponseDto.success();
    }
}
