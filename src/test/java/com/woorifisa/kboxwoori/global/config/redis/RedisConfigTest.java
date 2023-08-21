package com.woorifisa.kboxwoori.global.config.redis;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;

import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RedisConfigTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private final String KEY = "key";

    @AfterEach
    void afterAll() {
        redisTemplate.delete(KEY);
    }

    @Test
    public void string() throws Exception {
        ValueOperations<String, Object> string = redisTemplate.opsForValue();

        string.set(KEY, "value");

        Object value = string.get(KEY);

        assertEquals("value", value);
    }

    @Test
    public void list() throws Exception {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        list.rightPush(KEY, "value1");
        list.rightPush(KEY, "value2");
        list.rightPushAll(KEY, "value3", "value4");

        List<Object> expect = Arrays.asList("value1", "value2", "value3", "value4");

        assertEquals("value1" ,list.index(KEY, 0));
        assertEquals(4, list.size(KEY));
        assertEquals(expect, list.range(KEY, 0, 3));
    }

    @Test
    public void set() throws Exception {
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        set.add(KEY, "value1");
        set.add(KEY, "value1");
        set.add(KEY, "value2");

        HashSet<String> expect = new HashSet<>(Arrays.asList("value1", "value2"));

        assertEquals(2, set.size(KEY));
        assertEquals(expect, set.members(KEY));
    }

    @Test
    public void sortedSet() throws Exception {
        ZSetOperations<String, Object> zSet = redisTemplate.opsForZSet();
        zSet.add(KEY, "value1", 1);
        zSet.add(KEY, "value2", 2);
        zSet.add(KEY, "value1", 3);

        HashSet<String> expect = new HashSet<>(Arrays.asList("value2", "value1"));

        assertEquals(2, zSet.size(KEY));
        assertEquals(expect, zSet.range(KEY, 0, 1));
    }

    @Test
    public void hash() throws Exception {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        hash.put(KEY, "key1", "value1");
        hash.put(KEY, "key2", "value2");

        assertEquals("value1", hash.get(KEY, "key1"));
        assertEquals("value2", hash.get(KEY, "key2"));
        assertEquals(2, hash.size(KEY));
    }
}