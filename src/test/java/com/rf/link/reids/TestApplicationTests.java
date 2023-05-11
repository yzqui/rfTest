package com.rf.link.reids;

import com.rf.link.redis.RedisUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

@SpringBootTest
class TestApplicationTests {
//
//    @Resource
//    private RedisUtils redisUtils;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    
    @Test
    void redisTest() {
        redisTemplate.opsForValue().set("test_key", "test_value");
        redisTemplate.opsForValue().get("test_key");
    }
}