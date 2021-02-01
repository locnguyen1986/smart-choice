package com.nab.smartchoice.pricingserver.service;


import com.nab.smartchoice.pricingserver.redis.dto.ProductInfo;

public interface ProductService {

    ProductInfo getProductInfoBySKU(String sku);
}
