package com.woorifisa.kboxwoori.domain.event.repository;

import com.woorifisa.kboxwoori.domain.event.exception.DuplicateParticipationException;
import com.woorifisa.kboxwoori.global.exception.InternalServerErrorException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class EventRedisRepository {

    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisScript<String> joinEventScript;

    private static final String SUCCESS = "success";
    private static final String FAILED = "failed";
    private static final String DUPLICATED = "duplicated";

    public Boolean saveWinner(String key, String userId, Integer limit) {
        String result = redisTemplate.execute(joinEventScript, List.of(key), String.valueOf(limit), userId);

        if (SUCCESS.equals(result)) {
            return true;
        } else if (FAILED.equals(result)) {
            return false;
        } else if (DUPLICATED.equals(result)) {
            throw DuplicateParticipationException.EXCEPTION;
        } else {
            throw InternalServerErrorException.EXCEPTION;
        }
    }

    public Long getSize(String key) {
        return redisTemplate.opsForSet().size(key);
    }

    // 테스트용
    public void delete(String key) {
        redisTemplate.delete(key);
    }
}
