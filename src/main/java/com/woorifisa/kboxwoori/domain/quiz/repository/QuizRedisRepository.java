package com.woorifisa.kboxwoori.domain.quiz.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
@RequiredArgsConstructor
public class QuizRedisRepository {

    private final RedisTemplate<String, String> redisTemplate;
    private final static String QUIZ_KEY = "quiz:participant:";

    public void saveUserParticipation(String userId) {
        redisTemplate.opsForSet().add(getKey(), userId);
    }

    public Boolean isUserParticipated(String userId) {
        return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(getKey(), userId));
    }

    private static String getKey() {
        return QUIZ_KEY + LocalDate.now();
    }

}