package com.nab.smartchoice.dataconsumerserver.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductSKURequestKafkaMessage {

    private String sku;
    private UUID requestTrackerId;

}
