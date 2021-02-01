package com.nab.smartchoice.productserver.redis.dto;

import com.nab.smartchoice.productserver.redis.enumeration.RedisDomainEnum;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductInfo extends AbstractCacheableObject {

    private String sku;
    private String name;
    private String description;
    private List<String> imageUrl;
    private String url;

    @Override
    public String redisKey() {
        return sku;
    }

    @Override
    public String domain() {
        return RedisDomainEnum.REDIS_PRODUCT_INFO_DOMAIN.toString();
    }
}
