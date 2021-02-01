package com.nab.smartchoice.dataconsumerserver.kafka;

import com.nab.smartchoice.dataconsumerserver.kafka.dto.ProductSKURequestKafkaMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaListenerProcessor {

    @KafkaListener(topics = KafkaConstant.KAFKA_PRODUCT_SKU_REQUEST_TOPIC, groupId = KafkaConstant.KAFKA_PRODUCT_GROUP)
    public void listen(ProductSKURequestKafkaMessage message) {
        try {
            log.info("============MESSAGE PROCESS START==========");
            log.info(((ProductSKURequestKafkaMessage) message).getSku());
        } catch (Exception e) {
            log.error("Unable to process message, failed with exception", e);
        } finally {
            log.info("=============MESSAGE PROCESS END===========");
        }
    }


}
