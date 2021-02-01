package com.nab.smartchoice.dataconsumerserver.web.rest;

import com.nab.smartchoice.dataconsumerserver.service.DataConsumerService;
import com.nab.smartchoice.dataconsumerserver.web.rest.vm.DataConsumerRequestVM;
import io.swagger.annotations.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductConsumerController {

    private final DataConsumerService dataConsumerService;

    @GetMapping(value = "/data-consume")
    @ResponseStatus(HttpStatus.OK)
    public void consumerDataViaRest(DataConsumerRequestVM dataConsumerRequestVM) {
        dataConsumerService.consumeProductData(dataConsumerRequestVM);
    }

}
