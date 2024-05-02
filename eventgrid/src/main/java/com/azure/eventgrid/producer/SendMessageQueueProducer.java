package com.azure.eventgrid.producer;

import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusSenderAsyncClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SendMessageQueueProducer {
    @Autowired
    private ServiceBusSenderAsyncClient internalSender;

    public void sendQueueMessage(String content) {
        log.info("Sending Rpa dela line: {}", content);
        ServiceBusMessage serviceBusMessage = new ServiceBusMessage(content);

        internalSender
                .sendMessage(serviceBusMessage)
                .subscribe(
                        v -> log.info("Message Id: {} | Sent message: {}", serviceBusMessage.getMessageId(), serviceBusMessage),
                        e -> log.error("Error occurred while sending message id {}", serviceBusMessage.getMessageId(), e),
                        () -> log.info(" Message Id: {} | Sending message to AOM complete.", serviceBusMessage.getMessageId()));
    }
}
