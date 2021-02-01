package com.nab.smartchoice.dataconsumerserver.service;

import com.nab.smartchoice.dataconsumerserver.web.rest.vm.DataConsumerRequestVM;

public interface DataConsumerService {

    void consumeProductData(DataConsumerRequestVM dataConsumerRequestVM);
}
