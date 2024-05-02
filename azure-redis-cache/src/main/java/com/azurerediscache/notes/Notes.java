package com.azurerediscache.notes;

public class Notes {
    /*
    redis best documentation
  1.  https://caseyscarborough.com/blog/2014/12/18/caching-data-in-spring-using-redis/

  @Autowired
    private StringRedisTemplate redisTemplate;

    used to store values in redis cache like key and value pair

    4) "madhu"
   5) "tana"

    @Autowired
    private RedisTemplate redisTemplate;

    it is used to store values in String , object

    "\xac\xed\x00\x05t\x00\x05sudha" key

    The value is below

    ""\xac\xed\x00\x05sr\x00\x11java.lang.Integer\x12\xe2\xa0\xa4\xf7\x81\x878\x02\x00\x01I\x00\x05valuexr\x00\x10java.lang.Number\x86\xac\x95\x1d\x0b\x94\xe0\x8b\x02\x00\x00xp\x00\x00\x00\n"

RedisTemplate redisTemplate; if you want store keys like String format use below code

  redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());



     */

}
