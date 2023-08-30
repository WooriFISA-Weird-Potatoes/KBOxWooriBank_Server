package com.woorifisa.kboxwoori.domain.prediction.controller;

import com.woorifisa.kboxwoori.domain.prediction.dto.PredictionRequestDto;
import com.woorifisa.kboxwoori.domain.prediction.dto.PredictionResponseDto;
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
        if (principalDetails == null) {
            throw NotAuthenticatedAccountException.EXCEPTION;
        }

        PredictionResponseDto prediction = predictionService.getPrediction(principalDetails.getUsername());
        if (predictionService.IsPredictionOngoing()) {
            prediction.setIsEnded(true);
        }

        return ResponseDto.success(prediction);
    }

}
