package com.rf.link.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtils {

    @Resource
    public RedisTemplate<String, Object> redisTemplate;

    @Resource
    public StringRedisTemplate stringRedisTemplate;

    /**
     * 添加一个key，无过期时间
     *
     * @param key
     * @param value
     */
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 添加value为字符串的数据
     *
     * @param key
     * @param value
     */
    public void setString(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    /**
     * 添加value为字符串的数据
     *
     * @param key
     * @param value
     */
    public void setString(String key, String value, long time, TimeUnit timeUnit) {
        stringRedisTemplate.opsForValue().set(key, value, time, timeUnit);
    }

    /**
     * 获取value为字符串的数据
     *
     * @param key
     * @return
     */
    public String getString(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 添加一个key，并设置过期时间
     *
     * @param key
     * @param value
     * @param time
     * @param timeUnit
     */
    public void set(String key, Object value, long time, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, time, timeUnit);
    }

    /**
     * get redis value
     *
     * @param key
     * @return
     */
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 设置key的过期时间
     *
     * @param key
     * @param time
     * @param timeUnit
     */
    public Boolean expire(String key, long time, TimeUnit timeUnit) {
        return redisTemplate.expire(key, time, timeUnit);
    }

    /**
     * 删除key
     *
     * @param key
     */
    public Boolean delete(String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 判断key是否存在
     *
     * @param key
     * @return
     */
    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 设置hash
     */
    public void setHash(String key, Map<String, Object> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * 设置hash
     */
    public void setHash(String key, String hashKey, Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    /**
     * 判断hash中是否存在某个key
     */
    public Boolean hasHash(String key, String hashKey) {
        return redisTemplate.opsForHash().hasKey(key, hashKey);
    }

    /**
     * 获取hash
     */
    public Map<Object, Object> getHash(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 获取hash某个key的值
     */
    public Object getHash(String key, String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    /**
     * 删除hash中的某个key
     */
    public Object deleteHash(String key, String hashKey) {
        return redisTemplate.opsForHash().delete(key, hashKey);
    }

    /**
     * increment
     *
     * @param key
     * @return
     */
    public Long increment(String key) {
        return redisTemplate.opsForValue().increment(key);
    }

    /**
     * increment
     *
     * @param key
     * @param delta
     * @return
     */
    public Long increment(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * decrement
     *
     * @param key
     * @return
     */
    public Long decrement(String key) {
        return redisTemplate.opsForValue().decrement(key);
    }

    /**
     * decrement
     *
     * @param key
     * @param delta
     * @return
     */
    public Long decrement(String key, long delta) {
        return redisTemplate.opsForValue().decrement(key, delta);
    }

    /**
     * list right push
     * @param key
     * @param value
     */
    public void listPush(String key, String value) {
        stringRedisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * list left pop string
     * @param key
     * @return
     */
    public String listPop(String key) {
        return stringRedisTemplate.opsForList().leftPop(key);
    }

    public List<String> range(String key) {
        return stringRedisTemplate.opsForList().range(key, 0, -1);
    }

    public Set<String> keys(String keyPrefix) {
        return redisTemplate.keys(keyPrefix + "*");
    }
}