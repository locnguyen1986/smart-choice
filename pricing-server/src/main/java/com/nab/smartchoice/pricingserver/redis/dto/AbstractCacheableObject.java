package com.nab.smartchoice.pricingserver.redis.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;

import java.io.Serializable;

@Getter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({@JsonSubTypes.Type(value = ProductInfo.class, name = "productInfo"), @JsonSubTypes.Type(value = ProductPricing.class, name = "productPricing")})
public abstract class AbstractCacheableObject implements Serializable {

    public abstract String redisKey();

    public abstract String domain();

}
