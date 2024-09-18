package com.example;

import com.example.redis.pojo.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Map;

@SpringBootTest
public class RedisStringTest {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static final ObjectMapper mapper = new ObjectMapper();
    @Test
    void testString() {
        // key一般都是string类型，所以key的序列化一般用
        stringRedisTemplate.opsForValue().set("name", "胡歌");  //set方法可以接收任何类型的对象，然后帮我们转换成redis可以处理的字节
        System.out.println(stringRedisTemplate.opsForValue().get("name"));
    }

    @Test
    void testSaveUser() throws JsonProcessingException {
        User user = new User("虎哥", 21);

        //手动序列化为json字符串  这样可以只存储类本身，少占空间
        String json = mapper.writeValueAsString(user);

        stringRedisTemplate.opsForValue().set("User:200", json);

        String jsonUser = stringRedisTemplate.opsForValue().get("User:200");
        //手动反序列化
        User o = mapper.readValue(jsonUser, User.class);
        System.out.printf("o = " + o);
    }

    @Test
    void testHash() {
        stringRedisTemplate.opsForHash().put("User:400", "name", "虎哥");
        stringRedisTemplate.opsForHash().put("User:400", "age", "21");

        Map<Object, Object> entries = stringRedisTemplate.opsForHash().entries("User:400");
        System.out.println(entries);

    }
    @Test
    void contextLoads() {
    }
}
