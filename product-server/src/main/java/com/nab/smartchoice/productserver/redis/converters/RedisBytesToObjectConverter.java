package com.nab.smartchoice.productserver.redis.converters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nab.smartchoice.productserver.redis.dto.AbstractCacheableObject;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;

@Component
@ReadingConverter
public class RedisBytesToObjectConverter implements Converter<byte[], AbstractCacheableObject> {

    private final Jackson2JsonRedisSerializer<AbstractCacheableObject> serializer;

    public RedisBytesToObjectConverter() {
        serializer = new Jackson2JsonRedisSerializer<>(AbstractCacheableObject.class);
        serializer.setObjectMapper(new ObjectMapper());
    }

    @Override
    public AbstractCacheableObject convert(byte[] value) {
        return serializer.deserialize(value);
    }
}