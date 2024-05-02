package com.azurerediscache.service;

import com.azurerediscache.model.Order;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@Slf4j
public class EntitiesRedisCacheService {

    @Autowired
    private RedisTemplate redisTemplate;

    private ObjectMapper objectMapper=new ObjectMapper();


    public Order getEntityFromRedisCache(String key) {
        ValueOperations valueOperations=this.redisTemplate.opsForValue();
        return Order.class.cast(valueOperations.get(key));
    }

    public void setEntityToRedisCache(Order order) {
        ValueOperations valueOperations=this.redisTemplate.opsForValue();
        valueOperations.set("order",order);

    }
}
