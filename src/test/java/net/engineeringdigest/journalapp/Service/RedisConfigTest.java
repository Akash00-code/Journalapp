package net.engineeringdigest.journalapp.Service;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisConfigTest {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Test
    @Disabled
    public void testRedisConfig() {
        redisTemplate.opsForValue().set("name","akash");
        Object name = redisTemplate.opsForValue().get("name");
        assertNotNull(name);
        //assertEquals("akash", name.toString());
    }
}
