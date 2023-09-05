package com.woorifisa.kboxwoori.domain.prediction.repository;

import com.woorifisa.kboxwoori.domain.prediction.exception.InvalidPredictionParticipationTimeException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PredictionRedisRepository {

    private final RedisTemplate<String, String> redisTemplate;

    private static final String KEY = "prediction:";
    private static final String INFO_KEY = "prediction:info:";
    private static final String COUNT = "match-count";
    private static final String END_TIME = "end-time";

    public Boolean hasParticipated(String userId) {
        return redisTemplate.opsForHash().hasKey(getKey(KEY), userId);
    }

    public List<String> getPrediction(String userId) {
        String predictions = (String) redisTemplate.opsForHash().get(getKey(KEY), userId);
        if (predictions == null) {
            return null;
        }
        return Arrays.asList(predictions.substring(1, predictions.length() - 1).split(", "));
    }

    public void savePrediction(String userId, List<String> predictions) {
        redisTemplate.opsForHash().put(getKey(KEY), userId, predictions.toString());
    }

    public Integer getMatchCount() {
        Object matchCount = redisTemplate.opsForHash().get(getKey(INFO_KEY), COUNT);
        if (matchCount == null) {
            throw InvalidPredictionParticipationTimeException.EXCEPTION;
        }
        return Integer.valueOf(matchCount.toString());
    }

    public LocalTime getEndTime() {
        Object endTime = redisTemplate.opsForHash().get(getKey(INFO_KEY), END_TIME);
        if (endTime == null) {
            throw InvalidPredictionParticipationTimeException.EXCEPTION;
        }
        return LocalTime.parse(endTime.toString());
    }

    private String getKey(String key) {
        return key + LocalDate.now();
    }

}
