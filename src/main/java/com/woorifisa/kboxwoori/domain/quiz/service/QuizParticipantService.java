package com.woorifisa.kboxwoori.domain.quiz.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class QuizParticipantService {

    private final RedisTemplate<String, String> redisTemplate;
    private final static String QUIZ_KEY = "quiz:participant:";

    public void saveUserParticipation(String userId) {
        // 오늘 날짜 얻어 옴.
        String key = getKey();

        // Redis에 데이터 저장
        redisTemplate.opsForSet().add(key, userId);
    }

    public Set<String> getAllParticipants() {
        // 오늘 날짜 얻어 옴.
        String key = getKey();

        return redisTemplate.opsForSet().members(key);
    }

    public Boolean isUserParticipated(String userId) {
        // 오늘 날짜 얻어 옴.
        String key = getKey();
        return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(key, userId));
    }

    private static String getKey() {
        String formattedTime = getCurrentTime();

        // 저장된 참가자 목록을 가져옴
        String key = QUIZ_KEY + formattedTime;
        return key;
    }

    private static String getCurrentTime() {
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
        return currentTime.format(formatter);
    }
}
