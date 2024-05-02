package com.azurerediscache.controller;

import com.azurerediscache.service.RedisCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/redis")
public class RedisCacheController {

    @Autowired
    private RedisCacheService redisCacheService;

    @GetMapping(value = "/set-pair")
    public String setStringToRedisCache(@RequestParam("key") String key,
                                            @RequestParam("value") String value)
    {
        redisCacheService.setStringToRedisCache(key,value);
        return "value Added successfully";
    }

    @GetMapping("/get-value")
    public String setStringToRedisCache(@RequestParam("key") String key)
    {
       return redisCacheService.getStringToRedisCache(key);
    }

    @GetMapping(value = "/set-pair-int")
    public String setIntegerToRedisCache(@RequestParam("key") String key,
                                        @RequestParam("value") Integer value)
    {
        redisCacheService.setIntegerToRedisCache(key,value);
        return "value Added successfully";
    }

    @GetMapping(value = "/get-int")
    public Integer getIntegerToRedisCache(@RequestParam("key") String key)
    {
        return redisCacheService.getIntegerToRedisCache(key);
    }
}
