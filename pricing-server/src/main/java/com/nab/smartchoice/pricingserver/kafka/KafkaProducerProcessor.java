package com.nab.smartchoice.pricingserver.kafka;

import com.nab.smartchoice.pricingserver.kafka.dto.ProductSKURequestKafkaMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaProducerProcessor {

    private final KafkaTemplate kafkaTemplate;

    public void sendMessageAsync(final ProductSKURequestKafkaMessage productSKURequestKafkaMessage) {
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(KafkaConstant.KAFKA_PRODUCT_SKU_REQUEST_TOPIC, productSKURequestKafkaMessage);

        future.addCallback(new ListenableFutureCallback<>() {
            public void onSuccess(SendResult<String, String> result) {
                log.info("Sent message=[" + productSKURequestKafkaMessage + "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }

            public void onFailure(Throwable ex) {
                log.info("Unable to send message=[" + productSKURequestKafkaMessage + "] due to : " + ex.getMessage());
            }
        });
    }


}
