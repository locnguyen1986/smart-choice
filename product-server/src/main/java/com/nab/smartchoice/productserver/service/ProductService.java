package com.nab.smartchoice.productserver.service;


import com.nab.smartchoice.productserver.redis.dto.ProductInfo;

public interface ProductService {

    ProductInfo getProductInfoBySKU(String sku);
}
