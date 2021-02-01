package com.nab.smartchoice.productserver.service.impl;


import com.nab.smartchoice.productserver.kafka.KafkaProducerProcessor;
import com.nab.smartchoice.productserver.kafka.dto.ProductSKURequestKafkaMessage;
import com.nab.smartchoice.productserver.redis.RedisService;
import com.nab.smartchoice.productserver.redis.dto.ProductInfo;
import com.nab.smartchoice.productserver.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final RedisService redisService;
    private final KafkaProducerProcessor kafkaProducerProcessor;

    @Override
    public ProductInfo getProductInfoBySKU(String sku) {
        ProductInfo productInfo = (ProductInfo) redisService.findCacheableObjectByKey(ProductInfo.builder().sku(sku).build());
        if(productInfo == null) {
            ProductSKURequestKafkaMessage productSKURequestKafkaMessage = ProductSKURequestKafkaMessage.builder().sku(sku).requestTrackerId(UUID.randomUUID()).build();
            kafkaProducerProcessor.sendMessageAsync(productSKURequestKafkaMessage);
        }
        return productInfo;
    }
}

