package com.nab.smartchoice.productserver.redis.dto;

import com.nab.smartchoice.productserver.redis.enumeration.RedisDomainEnum;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductPricing extends AbstractCacheableObject  {

    private String sku;
    private String merchantCode;
    private Double originalPrice;
    private Double discountPrice;
    private String promotionCode;
    private Long lastUpdatedTime;
    private Boolean active;

    @Override
    public String redisKey() {
        return merchantCode;
    }

    @Override
    public String domain() {
        return RedisDomainEnum.REDIS_PRODUCT_INFO_DOMAIN + "##" + sku ;
    }
}
