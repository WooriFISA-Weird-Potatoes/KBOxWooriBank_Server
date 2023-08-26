package com.woorifisa.kboxwoori.domain.event.repository;

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

    public Boolean saveWinner(String key, String userId, Integer limit) throws Exception {
        String result = redisTemplate.execute(joinEventScript, List.of(key), String.valueOf(limit), userId);
        if ("success".equals(result)) {
            return true;
        } else if ("failed".equals(result)) {
            return false;
        } else if ("duplicated".equals(result)) {
            return false;
        } else {
            throw new Exception();
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
