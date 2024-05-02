package com.azure.asb.topic.listner;

import com.azure.asb.topic.model.Order;
import com.azure.messaging.servicebus.ServiceBusReceivedMessage;
import com.azure.messaging.servicebus.ServiceBusReceiverClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class MultipleMessageListener {
    @Autowired
    private ServiceBusReceiverClient topicSubscriber;

    public List<Order> multipleMessageListener()
    {
        List<ServiceBusReceivedMessage> serviceBusReceivedMessages= topicSubscriber.receiveMessages(10).stream().collect(Collectors.toList());
        return serviceBusReceivedMessages.stream().map(serviceBusReceivedMessage -> serviceBusReceivedMessage.getBody().toObject(Order.class))
                .collect(Collectors.toList());
    }
}
