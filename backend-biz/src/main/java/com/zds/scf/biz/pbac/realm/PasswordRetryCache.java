/*
 *
 * Copyright (c) 2016 All Rights Reserved
 */

/*
 * 修订记录:
 * 2016-10-31 20:53 创建
 */
package com.zds.scf.biz.pbac.realm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class PasswordRetryCache {

    @Autowired
    private RedisTemplate<String, Integer> redisTemplate;

    public Integer getRetryCount(String username) {
        String key = buildKey(username);
        Integer retryCount = redisTemplate.opsForValue().get(key);
        if (retryCount == null) {
            retryCount = 0;
        }
        return retryCount;
    }

    public void clearRetryCount(String username) {
        String key = buildKey(username);
        redisTemplate.delete(key);
    }

    public void setRetryCount(String username,Integer retryCount) {
        String key = buildKey(username);
        redisTemplate.opsForValue().set(key, retryCount);
    }

    private String buildKey(String username) {
        return  username;
    }
}
