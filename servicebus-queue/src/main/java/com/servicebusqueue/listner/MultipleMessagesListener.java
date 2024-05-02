package com.servicebusqueue.listner;

import com.azure.messaging.servicebus.ServiceBusReceivedMessage;
import com.azure.messaging.servicebus.ServiceBusReceiverClient;
import com.servicebusqueue.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MultipleMessagesListener {

    @Autowired
    ServiceBusReceiverClient queueSubscriber;

    @Autowired
    ServiceBusReceiverClient peekQueueSubscriber;

    public List<Order> multipleMessageListener()
    {
        List<ServiceBusReceivedMessage> serviceBusReceivedMessages= queueSubscriber.receiveMessages(10).stream().toList();
        return serviceBusReceivedMessages.stream().map(serviceBusReceivedMessage -> serviceBusReceivedMessage.getBody().toObject(Order.class))
                .collect(Collectors.toList());
    }

    public List<Order> peekMultipleMessages()
    {
        List<ServiceBusReceivedMessage> serviceBusReceivedMessages= peekQueueSubscriber.peekMessages(10).stream().toList();
        return serviceBusReceivedMessages.stream().map(serviceBusReceivedMessage -> serviceBusReceivedMessage.getBody().toObject(Order.class))
                .collect(Collectors.toList());
    }

}
