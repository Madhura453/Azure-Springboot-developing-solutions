package com.azurestorageaccount.service;

import com.azure.core.http.rest.PagedIterable;
import com.azure.storage.queue.QueueAsyncClient;
import com.azure.storage.queue.QueueClient;
import com.azure.storage.queue.models.PeekedMessageItem;
import com.azure.storage.queue.models.QueueMessageItem;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class QueueServiceForStorageAccountOne {

    @Autowired
    private QueueClient storageQueueClient;

    QueueAsyncClient storageAyncQueueClient;
    //

    public void sendMessagesToQueue(List<String> messages) {

        messages.stream().forEach(meaasge -> {
            log.info("sending message to azure queue{}", meaasge);
            String encodemessage=Base64.getEncoder().encodeToString(meaasge.getBytes(StandardCharsets.UTF_8));
            storageQueueClient.sendMessage(encodemessage);

        });



    }

    public List<String> peekMessagesFromQueue() {
        List<String> peekMessages = new ArrayList<>();
        PagedIterable<PeekedMessageItem> messages = storageQueueClient.peekMessages(2, Duration.ofSeconds(20), null);
        messages.stream().forEach(message -> {
            log.info("peek message is : {}",message.getMessageId());
           peekMessages.add(message.getBody().toString());
        });


        return peekMessages;
    }

    public List<String> receivedMessageFromQueue() {
        List<String> receivedMessages=new ArrayList<>();
        PagedIterable<QueueMessageItem> messages=storageQueueClient.receiveMessages(4);
        messages.stream().forEach(message->{
            log.info("received message is : {}",message.getMessageId());
            receivedMessages.add(message.getBody().toString());
        });
        return receivedMessages;
    }


}

/*
please note refer QueueAsyncClient
refer service bus async client for subscribe and flatmap
 */
