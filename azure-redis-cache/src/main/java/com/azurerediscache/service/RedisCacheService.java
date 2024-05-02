package com.azurerediscache.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RedisCacheService {
    @Autowired
    private RedisTemplate redisTemplate;

//    public RedisCacheService()
//    {
//        this.redisTemplate.setKeySerializer(new StringRedisSerializer());
//    }

    public void setStringToRedisCache(String key, String value) {
      ValueOperations valueOperations=this.redisTemplate.opsForValue();
      valueOperations.set(key,value);
    }

    public String getStringToRedisCache(String key) {
        ValueOperations valueOperations=this.redisTemplate.opsForValue();
        try {
           String s= new ObjectMapper().writeValueAsString(valueOperations.get(key));
            return new ObjectMapper().readValue(s,String.class);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public void setIntegerToRedisCache(String key, Integer value) {
        // below code is set key value as string instead of
        // "\xac\xed\x00\x05t\x00\x04mana"
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        ValueOperations valueOperations=this.redisTemplate.opsForValue();
        valueOperations.set(key,value);
    }


    public Integer getIntegerToRedisCache(String key) {
        ValueOperations valueOperations=this.redisTemplate.opsForValue();
        try {
            String s= new ObjectMapper().writeValueAsString(valueOperations.get(key));
            log.info("value is{}",s);
            return new ObjectMapper().readValue(s,Integer.class);
        } catch (JsonProcessingException e) {
            log.info("jsonProcessing Exception{}",e.getMessage());
            return null;
        }
    }
}
