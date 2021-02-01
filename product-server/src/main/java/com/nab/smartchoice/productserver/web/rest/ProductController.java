package com.nab.smartchoice.productserver.web.rest;


import com.nab.smartchoice.productserver.domain.Product;
import com.nab.smartchoice.productserver.redis.dto.ProductInfo;
import com.nab.smartchoice.productserver.repository.ProductRepository;
import com.nab.smartchoice.productserver.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ProductController  {

    private final ProductRepository productRepository;
    private final ProductService productService;

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public ResponseEntity<List<Product>> findByIds() {
        List<Product> products = productRepository.findAll();
        return ResponseEntity.ok().body(products);
    }

    @RequestMapping(value = "/products/{sku}", method = RequestMethod.GET)
    public ResponseEntity<ProductInfo> getProductInfoBySku(@PathVariable String sku) {
        ProductInfo productInfo = productService.getProductInfoBySKU(sku);
        return ResponseEntity.ok().body(productInfo);
    }


}
