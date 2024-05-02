package com.azurerediscache.controller;

import com.azurerediscache.model.Order;
import com.azurerediscache.service.EntitiesRedisCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/redis")
public class EntitiesRedisCacheController {

    @Autowired
    private EntitiesRedisCacheService entitiesRedisCacheService;

    @PostMapping(value = "/set-entity")
    public String setEntityToRedisCache(@RequestBody Order order)
    {
       entitiesRedisCacheService.setEntityToRedisCache(order);
        return "value Added successfully";
    }

    @GetMapping("/get-entity")
    public Order setStringToRedisCache(@RequestParam("key") String key)
    {
        return entitiesRedisCacheService.getEntityFromRedisCache(key);
    }
}
