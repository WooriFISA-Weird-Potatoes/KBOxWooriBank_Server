package com.woorifisa.kboxwoori.domain.prediction.service;

import com.woorifisa.kboxwoori.domain.prediction.dto.PredictionRequestDto;
import com.woorifisa.kboxwoori.domain.prediction.dto.PredictionResponseDto;
import com.woorifisa.kboxwoori.domain.prediction.exception.AllPredictionsRequiredException;
import com.woorifisa.kboxwoori.domain.prediction.exception.InvalidPredictionParticipationTimeException;
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

    public Boolean IsPredictionOngoing() {
        if (LocalTime.now().isAfter(predictionRedisRepository.getEndTime())) {
            throw InvalidPredictionParticipationTimeException.EXCEPTION;
        }
        return true;
    }

    public void savePrediction(String userId, PredictionRequestDto predictionRequestDto) {
        //TODO: 모두 예측했는지 확인 (경기 수는 가변값)
        if (predictionRequestDto.getPredictions().size() != predictionRedisRepository.getMatchCount()) {
            throw AllPredictionsRequiredException.EXCEPTION;
        }
        predictionRedisRepository.savePrediction(userId, predictionRequestDto.getPredictions());
    }
}
