package com.woorifisa.kboxwoori.domain.prediction.service;

import com.woorifisa.kboxwoori.domain.prediction.dto.PredictionRequestDto;
import com.woorifisa.kboxwoori.domain.prediction.dto.PredictionResponseDto;
import com.woorifisa.kboxwoori.domain.prediction.exception.AllPredictionsRequiredException;
import com.woorifisa.kboxwoori.domain.prediction.repository.PredictionRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PredictionService {

    private final PredictionRedisRepository predictionRedisRepository;

    public PredictionResponseDto getPrediction(String userId) {
        if (predictionRedisRepository.hasParticipated(userId)) {
            List<String> predictions = predictionRedisRepository.getPrediction(userId);
            if (predictions != null) {
                return PredictionResponseDto.builder()
                        .participated(true)
                        .predictions(predictions)
                        .build();
            }
        }

        return PredictionResponseDto.builder()
                .participated(false)
                .build();
    }

    public Boolean isPredictionEnded() {
        return LocalTime.now().isAfter(predictionRedisRepository.getEndTime());
    }

    public void savePrediction(String userId, PredictionRequestDto predictionRequestDto) {
        if (predictionRequestDto.getPredictions().size() < predictionRedisRepository.getMatchCount()) {
            throw AllPredictionsRequiredException.EXCEPTION;
        }
        predictionRedisRepository.savePrediction(userId, predictionRequestDto.getPredictions());
    }
}
