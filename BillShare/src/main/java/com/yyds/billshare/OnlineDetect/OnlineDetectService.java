package com.yyds.billshare.OnlineDetect;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class OnlineDetectService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public boolean setUserOnline(String userEmail, String sessionId){
        boolean result = false;
        try{
            redisTemplate.opsForValue().set(userEmail, sessionId);
            redisTemplate.expire(userEmail,20, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
    public boolean deleteUser(String userEmail){
        String sessionID = null;
        try{
            sessionID = redisTemplate.opsForValue().getAndDelete(userEmail);
        } catch (Exception e){
            e.printStackTrace();
        }
        return sessionID != null;
    }

    public Boolean isOnline(String userEmail){
        return redisTemplate.hasKey(userEmail);
    }
    public String getUserWSSessionID(String userEmail){
        Object sessionID = null;
        try{
            sessionID = redisTemplate.opsForValue().get(userEmail);
        } catch (Exception e){
            e.printStackTrace();
        }
        if(sessionID == null)
            return null;
        return sessionID.toString();
    }
}
