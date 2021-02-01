package com.nab.smartchoice.pricingserver.redis;

import com.nab.smartchoice.pricingserver.redis.converters.RedisBytesToObjectConverter;
import com.nab.smartchoice.pricingserver.redis.converters.RedisObjectToBytesConverter;
import com.nab.smartchoice.pricingserver.redis.dto.AbstractCacheableObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisService {


    private final RedisTemplate redisTemplate;
    private final RedisObjectToBytesConverter redisObjectToBytesConverter;
    private final RedisBytesToObjectConverter redisBytesToObjectConverter;

    public void saveRedisObject(final AbstractCacheableObject object) {
        log.info("Received through Queue: " + object.toString());
        try {
            final HashOperations hashOperations = redisTemplate.opsForHash();
            hashOperations.put(object.domain(), object.redisKey(), redisObjectToBytesConverter.convert(object));
        } catch (Exception e) {
            log.error("Unable to save object to REDIS cache, exception: ", e);
        }
    }

    public AbstractCacheableObject findCacheableObjectByKey(final AbstractCacheableObject object) {
        try {
            final HashOperations hashOperations = redisTemplate.opsForHash();
            Object receivedMessage = hashOperations.get(object.domain(), object.redisKey());
            if(receivedMessage == null) {
                return null;
            }
            return redisBytesToObjectConverter.convert((byte[])receivedMessage );
        } catch (Exception e) {
            log.error("Unable to retrieve object from REDIS cache, exception: ", e);
        }
        return null;
    }

}
