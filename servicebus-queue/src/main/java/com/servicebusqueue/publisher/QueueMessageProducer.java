package com.servicebusqueue.publisher;

import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusMessageBatch;
import com.azure.messaging.servicebus.ServiceBusSenderAsyncClient;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.servicebusqueue.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class QueueMessageProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(QueueMessageProducer.class);

    @Autowired
    private ServiceBusSenderClient senderClient;

    ServiceBusSenderAsyncClient senderAynClient;

    public void sendQueueMessage(Order order) {

        String methodName = "sendQueueMessage";
        String event = "";

        try {
            event = new ObjectMapper().writeValueAsString(order);
            LOGGER.info("MethodName : {} | Sending ShipmentActuals: {}", methodName, event);
        } catch (JsonProcessingException e) {
            LOGGER.error("MethodName: {} | Error while parsing data: {}", methodName, ExceptionUtils.getStackTrace(e));
        }

        ServiceBusMessage serviceBusMessage = new ServiceBusMessage(event);

        senderAynClient
                .sendMessage(serviceBusMessage)
                .subscribe(
                        v ->
                                LOGGER.info("MethodName : {} | Sent message: {}", methodName, serviceBusMessage),
                        e ->
                                LOGGER.error("MethodName : {} | Error occurred while sending message", methodName, e),
                        () ->
                                LOGGER.info("MethodName : {} | Sent message to Internal Topic", methodName));
    }

    public void sendMultipleMessagesWithDefault(List<Order> orderList)
    {
        List<ServiceBusMessage> listOfMessages=createMessages(orderList);
        sendMultipleMessages(listOfMessages);

    }

    public void timeToLiveFeature(List<Order> orderList)
    {

        List<ServiceBusMessage> listOfMessages=createMessagesWithTimeToLiveFeature(orderList);
        sendMultipleMessages(listOfMessages);
    }

    public void duplicateDetection(List<Order> orderList)
    {
       List<ServiceBusMessage> listOfMessages=createMessagesWithDuplicateDetection(orderList);
        sendMultipleMessages(listOfMessages);
    }

    public void setApplicationPropertiesToMessages(List<Order> orderList)
    {
        List<ServiceBusMessage> listOfMessages=createMessagesWithApplicationProperties(orderList);
        sendMultipleMessages(listOfMessages);
    }

    private void sendMultipleMessages(List<ServiceBusMessage> listOfMessages) {
        ServiceBusMessageBatch messageBatch=senderClient.createMessageBatch();

        // Creates an ServiceBusMessageBatch where the ServiceBus.

        // create a list of messages

        // We try to add as many messages as a batch can fit based on the maximum size and send to Service Bus when
        // the batch can hold no more messages. Create a new batch for next set of messages and repeat until all
        // messages are sent.
        for (ServiceBusMessage message : listOfMessages) {
            if (messageBatch.tryAddMessage(message)) {
                continue;
            }
            // The batch is full, so we create a new batch and send the batch.
            senderClient.sendMessages(messageBatch);
            //System.out.println("Sent a batch of messages to the queue: " + queueName);
            System.out.println("Sent a batch of messages to the queue: ");

            // create a new batch
            messageBatch = senderClient.createMessageBatch();

            // Add that message that we couldn't before.
            if (!messageBatch.tryAddMessage(message)) {
                System.err.printf("Message is too large for an empty batch. Skipping. Max size: %s.", messageBatch.getMaxSizeInBytes());
            }
        }

        if (messageBatch.getCount() > 0) {
            senderClient.sendMessages(messageBatch);
           // System.out.println("Sent a batch of messages to the queue: " + queueName);
            System.out.println("Sent a batch of messages to the queue: ");
        }

        //close the client
        //senderClient.close();
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

    public List<ServiceBusMessage> createMessagesWithTimeToLiveFeature(List<Order> orderList)
    {
        return orderList.stream().map(order -> {
            try {
                String event = new ObjectMapper().writeValueAsString(order);
                ServiceBusMessage serviceBusMessage=new ServiceBusMessage(event);
                serviceBusMessage.setTimeToLive(Duration.ofSeconds(30));
                return serviceBusMessage;
            } catch (JsonProcessingException e) {
                LOGGER.error("Error while parsing data: {}",  ExceptionUtils.getStackTrace(e));
                return new ServiceBusMessage("");
            }
        }).collect(Collectors.toList());
    }


    public List<ServiceBusMessage> createMessagesWithDuplicateDetection(List<Order> orderList)
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

    public List<ServiceBusMessage> createMessagesWithApplicationProperties(List<Order> orderList)
    {
        return orderList.stream().map(order -> {
            try {
                String event = new ObjectMapper().writeValueAsString(order);
                ServiceBusMessage serviceBusMessage=new ServiceBusMessage(event);
                serviceBusMessage.setMessageId("D"+order.getOrderId());
                serviceBusMessage.getApplicationProperties().putAll(getsomeApplicationProperties());
                return serviceBusMessage;
            } catch (JsonProcessingException e) {
                LOGGER.error("Error while parsing data: {}",  ExceptionUtils.getStackTrace(e));
                return new ServiceBusMessage("");
            }
        }).collect(Collectors.toList());
    }

  public Map<String,Object> getsomeApplicationProperties()
  {
      Map<String,Object> map=new HashMap<>();
      map.put("EventType", "com.microsoft.samples.hello-event");
      map.put("priority", 1);
      map.put("score", 9.0);
      return map;


//      ServiceBusMessage message = new ServiceBusMessage(ServiceBusMessage(BinaryData.fromString("Hello"));
//      message.getApplicationProperties().put("EventType", "com.microsoft.samples.hello-event");
//      message.getApplicationProperties().put("priority", 1);
//      message.getApplicationProperties().put("score", 9.0);
  }

}
