package com.azure.asb.topic.publisher;

import com.azure.asb.topic.Controller.TopicController;
import com.azure.asb.topic.model.Order;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusMessageBatch;
import com.azure.messaging.servicebus.ServiceBusSenderAsyncClient;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SendMessageToTopic {

    private static final Logger logger = LoggerFactory.getLogger(TopicController.class);

    @Value("${azure.servicebus.madhura-topic}")
    private String topicName;

//    @Autowired
//    private JmsTemplate jmsTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(SendMessageToTopic.class);

    @Autowired
    private ServiceBusSenderAsyncClient senderClientAsync;

    @Autowired
    private ServiceBusSenderClient senderClient;

    public void sendSingleTopicMessage(Order order) {

        String methodName = "sendTopicMessage";

        String event = "";

        try {
            event = new ObjectMapper().writeValueAsString(order);
            LOGGER.info("MethodName : {} | Sending ActualShipmentEvent: {}", methodName, event);
        } catch (JsonProcessingException e) {
            LOGGER.error("MethodName: {} | Error while parsing data: {}", methodName, ExceptionUtils.getStackTrace(e));
        }

        ServiceBusMessage serviceBusMessage = new ServiceBusMessage(event);
        serviceBusMessage.setMessageId("madhura");

        senderClientAsync
                .sendMessage(serviceBusMessage)
                .subscribe(
                        v ->
                                LOGGER.info(
                                        "MethodName : {} | Order Release Id : {} | Message Id: {} | Sent message: {}",
                                        methodName,
                                        serviceBusMessage.getMessageId(),
                                        serviceBusMessage),
                        e ->
                                LOGGER.error(
                                        "MethodName : {} | Order Release Id : {} | Error occurred while sending message",
                                        methodName,
                                        e),
                        () ->
                                LOGGER.info(
                                        "MethodName : {} | Order Release Id : {} | Message Id: {} | Sending message to AOM complete.",
                                        methodName,
                                        serviceBusMessage.getMessageId()));
    }


    public void sendMultipleMessagesToTopicWithDefault(List<Order> orderList)
    {


        // create a list of messages
        List<ServiceBusMessage> listOfMessages = createMessages(orderList);

        sendMultipleMessagesToTopic(listOfMessages);

        //close the client
    }

    public void sendMultipleMessagesToTopicFilterExample(List<Order> orderList)
    {


        // create a list of messages
        List<ServiceBusMessage> listOfMessages = createMessagesWithMessageId(orderList);

        sendMultipleMessagesToTopic(listOfMessages);

        //close the client
    }

    private void sendMultipleMessagesToTopic(List<ServiceBusMessage> listOfMessages) {
        // Creates an ServiceBusMessageBatch where the ServiceBus.
        ServiceBusMessageBatch messageBatch = senderClient.createMessageBatch();

        // We try to add as many messages as a batch can fit based on the maximum size and send to Service Bus when
        // the batch can hold no more messages. Create a new batch for next set of messages and repeat until all
        // messages are sent.
        for (ServiceBusMessage message : listOfMessages) {
            if (messageBatch.tryAddMessage(message)) {
                continue;
            }

            // The batch is full, so we create a new batch and send the batch.
            senderClient.sendMessages(messageBatch);
            System.out.println("Sent a batch of messages to the topic: " + topicName);

            // create a new batch
            messageBatch = senderClient.createMessageBatch();

            // Add that message that we couldn't before.
            if (!messageBatch.tryAddMessage(message)) {
                System.err.printf("Message is too large for an empty batch. Skipping. Max size: %s.", messageBatch.getMaxSizeInBytes());
            }
        }

        if (messageBatch.getCount() > 0) {
            senderClient.sendMessages(messageBatch);
            System.out.println("Sent a batch of messages to the topic: " + topicName);
        }
    }

    public List<ServiceBusMessage> createMessages(List<Order> orderList)
    {
        return orderList.stream().map(order -> {
            try {
                String event = new ObjectMapper().writeValueAsString(order);
                ServiceBusMessage serviceBusMessage=new ServiceBusMessage(event);
                return serviceBusMessage;
            } catch (JsonProcessingException e) {
                LOGGER.error("Error while parsing data: {}",  ExceptionUtils.getStackTrace(e));
                return new ServiceBusMessage("");
            }
        }).collect(Collectors.toList());
    }

    public List<ServiceBusMessage> createMessagesWithMessageId(List<Order> orderList)
    {
        return orderList.stream().map(order -> {
            try {
                String event = new ObjectMapper().writeValueAsString(order);
                ServiceBusMessage serviceBusMessage=new ServiceBusMessage(event);
                serviceBusMessage.setMessageId("D"+order.getOrderId());
                return serviceBusMessage;
            } catch (JsonProcessingException e) {
                LOGGER.error("Error while parsing data: {}",  ExceptionUtils.getStackTrace(e));
                return new ServiceBusMessage("");
            }
        }).collect(Collectors.toList());
    }

    /*

     Below class is for spring.jms servicebus.connection-string auto configuration.
    No need to write ServiceBusReceiverAsyncClient explicitly

    public void postMessage(@RequestParam String message) {
        logger.info("Sending message");
        jmsTemplate.convertAndSend(topicName, message.getBytes(StandardCharsets.UTF_8));
    }

     */

}
