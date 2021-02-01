package com.nab.smartchoice.dataconsumerserver.service.impl;

import com.nab.smartchoice.dataconsumerserver.redis.dto.ProductPricing;
import com.nab.smartchoice.dataconsumerserver.redis.RedisService;
import com.nab.smartchoice.dataconsumerserver.service.DataConsumerService;
import com.nab.smartchoice.dataconsumerserver.web.rest.vm.DataConsumerRequestVM;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DataConsumerServiceImpl implements DataConsumerService {

    private final RedisService redisService;

    @Override
    public void consumeProductData(DataConsumerRequestVM dataConsumerRequestVM) {
        redisService.saveRedisObject(dataConsumerRequestVM.getProductInfo());
        for(ProductPricing productPricing : dataConsumerRequestVM.getProductPricings()) {
            redisService.saveRedisObject(productPricing);
        }
    }
}

