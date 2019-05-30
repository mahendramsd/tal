package lk.dmg.common.service;

import lk.dmg.common.model.Resort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class RedisHashMapAbstractService<E extends Serializable> {

    @Autowired
    RedisTemplate<String, E> redisTemplate;
    private HashOperations<String,String, E> hashOperations;//Hash,HashKey,HashValue
    Class<E> voClass;

    @PostConstruct
    void setProperties() {
        hashOperations = redisTemplate.opsForHash();
    }

    public void setVoClass(Class<E> redisVOClass) {
        this.voClass = redisVOClass;
        redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(voClass));
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(voClass));
        redisTemplate.afterPropertiesSet();

    }

    public void set(String hash, String hashKey, E value) {
        initSerializer();
        hashOperations.put(hash,hashKey,value);
    }
    public E get(String hash, String hashKey) {
        initSerializer();
       return hashOperations.get(hash,hashKey);
    }
    public List<E> getByKeySet(String hash,Set<String> hashKeySet) {
        initSerializer();
        return hashOperations.multiGet(hash,hashKeySet);
    }

    private void initSerializer() {
        redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(Resort.class));
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Resort.class));
    }

}
