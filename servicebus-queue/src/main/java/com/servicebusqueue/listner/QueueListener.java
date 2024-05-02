package com.servicebusqueue.listner;

import com.azure.messaging.servicebus.ServiceBusReceiverAsyncClient;
import com.azure.messaging.servicebus.ServiceBusReceiverClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.servicebusqueue.model.Order;
import com.servicebusqueue.service.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class QueueListener {

    @Autowired
    private ServiceBusReceiverAsyncClient queueSubscriberAync;


    @Autowired
    private ServiceBusReceiverClient queueSubscriber;

    @Autowired
    private ServiceBusReceiverAsyncClient peekQueueSubscriberAsync;

    @Autowired
    private QueueService queueService;
    private static final Logger LOGGER = LoggerFactory.getLogger( QueueListener.class);

    public void receiveQueueMessage() {
        String methodName = "receiveSubscriptionMessage";
        LOGGER.info("MethodName : {} | Listening queue message", methodName);

        queueSubscriberAync
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
                               queueService.setQueueMessage(order);
                                return queueSubscriberAync.complete(message);
                            } catch (Exception ex) {
                                LOGGER.error(
                                        "MethodName : {} | Exception {} caught | Error: {}",
                                        methodName,
                                        ex.getClass(),
                                        ex.getMessage());
                                return queueSubscriberAync.abandon(message);
                            }
                        })
                .subscribe(
                        message -> LOGGER.info("MethodName : {} | Message processed", methodName),
                        error -> LOGGER.info("MethodName : {} | Error occurred: ", methodName, error),
                        () -> LOGGER.info("MethodName : {} | Receiving complete", methodName));
    }


    public void peekQueueMessage() {
        String methodName = "receiveSubscriptionMessage";
        LOGGER.info("MethodName : {} | Listening queue message", methodName);

        peekQueueSubscriberAsync
                .peekMessage()
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
                                queueService.setQueueMessage(order);

                            } catch (Exception ex) {
                                LOGGER.error(
                                        "MethodName : {} | Exception {} caught | Error: {}",
                                        methodName,
                                        ex.getClass(),
                                        ex.getMessage());
                            }
                           // peekQueueSubscriberAsync.close();
                            return Mono.empty();
                        })
                .subscribe(
                        message -> LOGGER.info("MethodName : {} | Message processed", methodName),
                        error -> LOGGER.info("MethodName : {} | Error occurred: ", methodName, error),
                        () -> LOGGER.info("MethodName : {} | Receiving complete", methodName));
    }

    public void peekQueueMessageFlatmap() {
        String methodName = "receiveSubscriptionMessage";
        LOGGER.info("MethodName : {} | Listening queue message", methodName);

        peekQueueSubscriberAsync
                .peekMessages(10)
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
                                queueService.setQueueMessage(order);

                            } catch (Exception ex) {
                                LOGGER.error(
                                        "MethodName : {} | Exception {} caught | Error: {}",
                                        methodName,
                                        ex.getClass(),
                                        ex.getMessage());
                            }
                         //   peekQueueSubscriberAsync.close();
                            return Mono.empty();
                        })
                .subscribe(
                        message -> LOGGER.info("MethodName : {} | Message processed", methodName),
                        error -> LOGGER.info("MethodName : {} | Error occurred: ", methodName, error),
                        () -> LOGGER.info("MethodName : {} | Receiving complete", methodName));
    }


    public void getApplicationProperties()
    {
        String methodName = "receiveSubscriptionMessage";
        LOGGER.info("MethodName : {} | Listening queue message", methodName);
        //AtomicReference<Map<String, Object>> applicationProperties=null;
        queueSubscriberAync
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
                                queueService.setQueueMessage(order);
                               Map<String,Object> applicationproperties=message.getApplicationProperties();
                                LOGGER.info("application properties {}",applicationproperties.size());
                                LOGGER.info("application properties {}",new ObjectMapper().writeValueAsString(applicationproperties));
                                return queueSubscriberAync.complete(message);
                            } catch (Exception ex) {
                                LOGGER.error(
                                        "MethodName : {} | Exception {} caught | Error: {}",
                                        methodName,
                                        ex.getClass(),
                                        ex.getMessage());
                                return queueSubscriberAync.abandon(message);
                            }
                        })
                .subscribe(
                        message -> LOGGER.info("MethodName : {} | Message processed", methodName),
                        error -> LOGGER.info("MethodName : {} | Error occurred: ", methodName, error),
                        () -> LOGGER.info("MethodName : {} | Receiving complete", methodName));
    }

}
