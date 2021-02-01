package com.nab.smartchoice.pricingserver.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductSKURequestKafkaMessage extends AbstractKafkaMessage{

    private String sku;
    private UUID requestTrackerId;

}
