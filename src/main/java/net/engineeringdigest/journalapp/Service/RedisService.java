package net.engineeringdigest.journalapp.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalapp.Entity.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    public <T> T get(String key,Class<T>  entity)  {
        try{
            String s = redisTemplate.opsForValue().get(key);
            ObjectMapper mapper = new ObjectMapper();
            T t = mapper.readValue(s, entity);
            return t;
        }catch(Exception e){
            log.error(e.getMessage(),e);
            return null;
        }

    }

    public void set(String key,WeatherResponse response,Long ttl){
        try{
            ObjectMapper mapper = new ObjectMapper();
            String s = mapper.writeValueAsString(response);
            redisTemplate.opsForValue().set(key,s,ttl, TimeUnit.SECONDS);
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }

    }
}
