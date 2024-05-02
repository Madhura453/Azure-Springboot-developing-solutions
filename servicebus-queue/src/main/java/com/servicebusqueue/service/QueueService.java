package com.servicebusqueue.service;

import com.servicebusqueue.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class QueueService {

    private static Order order;
    public void setQueueMessage(Order qorder) {
        order=qorder;
    }

    public Order receiveQueueMessage() {
        return order;
    }

}
