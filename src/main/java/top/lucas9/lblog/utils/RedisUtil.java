package top.lucas9.lblog.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author lucas
 */
@Component
public class RedisUtil {
    @Autowired
    private RedisTemplate redisTemplate;

    public Boolean hasKey(Object key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Set keys(Object key) {
        return redisTemplate.keys(key);
    }

    public Long listRightPush(Object key, Object value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }
    public List listRange(Object key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    public Boolean zSetAdd(Object key, Object value, double score) {
        return redisTemplate.opsForZSet().add(key, value, score);
    }
    public Set getZSetRank(Object key, long start, long end) {
        return redisTemplate.opsForZSet().reverseRange(key, start, end);
    }
    public Long removeKey(Object key) {
        return redisTemplate.opsForZSet().removeRange(key, 0, -1);
    }

    public Boolean zSetExitMember(Object key, Object member) {
        Long rank = redisTemplate.opsForZSet().rank(key, member);
        return null != rank;
    }

    public Double zSetPlusScore(Object key, Object member, double score) {
        Double newScore = redisTemplate.opsForZSet().incrementScore(key, member, score);
        return newScore;
    }

    public void hashPut(Object key, Object item, Object value) {
        redisTemplate.opsForHash().put(key, item, value);
    }

    public Object hashGet(Object key, Object item) {
        return redisTemplate.opsForHash().get(key, item);
    }

    public boolean hashHasKey(Object key, Object item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }
    public void hashDel(String key, Object... item) {
        redisTemplate.opsForHash().delete(key, item);
    }
}
