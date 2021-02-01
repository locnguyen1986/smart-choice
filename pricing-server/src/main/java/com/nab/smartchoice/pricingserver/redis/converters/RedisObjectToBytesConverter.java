package com.nab.smartchoice.pricingserver.redis.converters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nab.smartchoice.pricingserver.redis.dto.AbstractCacheableObject;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;

@Component
@WritingConverter
public class RedisObjectToBytesConverter implements Converter<AbstractCacheableObject, byte[]> {

    private final Jackson2JsonRedisSerializer<AbstractCacheableObject> serializer;

    public RedisObjectToBytesConverter() {
        serializer = new Jackson2JsonRedisSerializer<>(AbstractCacheableObject.class);
        serializer.setObjectMapper(new ObjectMapper());
    }

    @Override
    public byte[] convert(AbstractCacheableObject value) {
        return serializer.serialize(value);
    }
}