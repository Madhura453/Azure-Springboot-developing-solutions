package com.azure.eventgrid.listner;

import com.azure.eventgrid.service.EventGridService;
import com.azure.messaging.servicebus.ServiceBusReceiverAsyncClient;
import com.azure.eventgrid.dto.EventGridEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EventGridListner {
    @Autowired
    private ServiceBusReceiverAsyncClient eventGridReceiver;

    @Autowired
    private EventGridService eventGridService;

    public void receiveQueueMessage() {
// filter upto blobs
        String methodName = "EventGrid receiveQueueMessage";
        log.info("MethodName : {} | Listening to Event Grid: Started", methodName);

        eventGridReceiver
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
                                EventGridEvent eventGridEvent = message.getBody().toObject(EventGridEvent.class);
                                String[] name = eventGridEvent.getSubject().split("/");
                                eventGridService.readEmployeeFile(name[name.length - 1]);
                                return eventGridReceiver.complete(message);
                            } catch (Exception ex) {
                                log.error(
                                        "MethodName : {} | Exception {} caught | Error: {}",
                                        methodName,
                                        ex.getClass(),
                                        ex.getMessage());
                                return eventGridReceiver.abandon(message);
                            }
                        })
                .subscribe(
                        message -> log.info("MethodName : {} | Message processed", methodName),
                        error -> log.info("MethodName : {} | Error occurred: ", methodName, error),
                        () -> log.info("MethodName : {} | Receiving complete", methodName));
    }

}


//  when storage account  file upload. via topic endpoint.
// event grid subscription  create in storage account container . the event handler is queue.
// topic will forward that input message to queue