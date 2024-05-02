package com.azure.asb.topic.listner;

import com.azure.asb.topic.model.Order;
import com.azure.messaging.servicebus.ServiceBusReceiverAsyncClient;
import com.azure.messaging.servicebus.ServiceBusReceiverClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
@Slf4j
public class TopicListner {

    private static final Logger LOGGER = LoggerFactory.getLogger(TopicListner.class);

    @Autowired
    private ServiceBusReceiverAsyncClient topicSubscrierAsync;

    @Autowired
    ServiceBusReceiverClient topicSubscriber;

    public void receiveSubscriptionMessage() {

        String methodName = "receiveSubscriptionMessage";
        LOGGER.info("MethodName : {} | Listening to Topic: Started", methodName);

        topicSubscrierAsync
                .receiveMessages()
                .flatMap(
                        message -> {
                            try {
                                LOGGER.info(
                                        "MethodName : {} | Received Sequence Number: {}, Received MessageId: {}",
                                        methodName,
                                        message.getSequenceNumber(),
                                        message.getMessageId());
                                LOGGER.info(
                                        "MethodName : {} | Contents of message as string: {}",
                                        methodName,
                                        message.getBody());
                                Order order = message.getBody().toObject(Order.class);
                                log.info("content of string is {}",new ObjectMapper().writeValueAsString(order));
                                return topicSubscrierAsync.complete(message);
                            } catch (Exception ex) {
                                LOGGER.error(
                                        "MethodName : {} | Exception {} caught | Error: {}",
                                        methodName,
                                        ex.getClass(),
                                        ex.getMessage());
                                return topicSubscrierAsync.abandon(message);
                            }
                        })
                .subscribe(
                        message -> LOGGER.info("MethodName : {} | Message processed", methodName),
                        error -> LOGGER.info("MethodName : {} | Error occurred: ", methodName, error),
                        () -> LOGGER.info("MethodName : {} | Receiving complete", methodName));
    }




    /*

    Below class is for spring.jms servicebus.connection-string auto configuration.
    No need to write ServiceBusReceiverAsyncClient explicitly

    @JmsListener(destination = "${azure.carrier.topic.name}", containerFactory = "jmsListenerContainerFactory")
    public void receiveMessage(String poMessage) throws IllegalAccessException {
        log.info("recevied message is " + poMessage.length());
        System.out.println(poMessage);
    }

     */



}
