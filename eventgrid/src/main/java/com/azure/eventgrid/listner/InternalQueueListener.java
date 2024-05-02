package com.azure.eventgrid.listner;

import com.azure.eventgrid.service.EventGridService;
import com.azure.messaging.servicebus.ServiceBusReceiverAsyncClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class InternalQueueListener {
    @Autowired
    private ServiceBusReceiverAsyncClient internalReceiver;

    @Autowired
    private EventGridService eventGridService;

    public void receiveQueueMessage() {

        String methodName = "internalQueue receiveQueueMessage";
        log.info("MethodName : {} | Listening to Internal Queue: Started", methodName);

       internalReceiver
                .receiveMessages()
                .flatMap(
                        message -> {
                            try {
                                log.info(
                                        "MethodName : {} | Received Sequence Number: {}, Received MessageId: {}",
                                        methodName,
                                        message.getSequenceNumber(),
                                        message.getMessageId());
                                log.info(
                                        "MethodName : {} | Contents of message as string: {}",
                                        methodName,
                                        message.getBody());
                                String rpaDealLine = message.getBody().toString();
                                log.info(
                                        "MethodName : {} | Contents of message after conversion: {}",
                                        methodName,
                                        rpaDealLine);
                                eventGridService.persistEmployeeObject(rpaDealLine);
                                return internalReceiver.complete(message);
                            } catch (Exception ex) {
                                log.error(
                                        "MethodName : {} | Exception {} caught | Error: {}",
                                        methodName,
                                        ex.getClass(),
                                        ex.getMessage());
                                return internalReceiver.abandon(message);
                            }
                        })
                .subscribe(
                        message -> log.info("MethodName : {} | Message processed", methodName),
                        error -> log.info("MethodName : {} | Error occurred: ", methodName, error),
                        () -> log.info("MethodName : {} | Receiving complete", methodName));
    }
}
