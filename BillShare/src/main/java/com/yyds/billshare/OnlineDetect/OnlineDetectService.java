package com.yyds.billshare.OnlineDetect;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class OnlineDetectService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();

    public boolean setUser(String userEmail, String userVal){
        boolean result = false;
        try{
            redisTemplate.opsForHash().put("1", userEmail, userVal);
            redisTemplate.expire("1",3, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
    public String getUser(String userEmial){
        return redisTemplate.opsForHash().get("1", userEmial).toString();
    }

}
