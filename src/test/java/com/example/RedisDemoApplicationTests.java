package com.example;

import com.example.redis.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class RedisDemoApplicationTests {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    void testString() {
        // key一般都是string类型，所以key的序列化一般用
        redisTemplate.opsForValue().set("name", "虎哥");  //set方法可以接收任何类型的对象，然后帮我们转换成redis可以处理的字节
        System.out.println(redisTemplate.opsForValue().get("name"));
    }

    @Test
    void testSaveUser() {
        redisTemplate.opsForValue().set("User:100", new User("虎哥", 21));
        User o = (User) redisTemplate.opsForValue().get("User:100");
        System.out.printf("o = " + o);
    }

    @Test
    void contextLoads() {
    }

}
