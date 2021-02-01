package com.nab.smartchoice.dataconsumerserver.web.rest.vm;

import com.nab.smartchoice.dataconsumerserver.redis.dto.ProductInfo;
import com.nab.smartchoice.dataconsumerserver.redis.dto.ProductPricing;
import lombok.*;

import java.util.List;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class DataConsumerRequestVM {


    ProductInfo productInfo;

    List<ProductPricing> productPricings;

}
