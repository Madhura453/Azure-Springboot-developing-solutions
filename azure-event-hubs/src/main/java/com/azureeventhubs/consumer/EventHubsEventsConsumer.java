package com.azureeventhubs.consumer;

import com.azure.messaging.eventhubs.EventData;
import com.azure.messaging.eventhubs.EventHubClientBuilder;
import com.azure.messaging.eventhubs.EventProcessorClient;
import com.azure.messaging.eventhubs.EventProcessorClientBuilder;
import com.azure.messaging.eventhubs.checkpointstore.blob.BlobCheckpointStore;
import com.azure.messaging.eventhubs.models.ErrorContext;
import com.azure.messaging.eventhubs.models.EventContext;
import com.azure.messaging.eventhubs.models.PartitionContext;
import com.azure.storage.blob.BlobContainerAsyncClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import com.azureeventhubs.model.Order;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.function.Consumer;

@Slf4j
@Component
public class EventHubsEventsConsumer {

    @Value("${azure.storage.account-connection-string}")
    private String storageConnectionString;

    @Value("${azure.storage.eventhub-checkpoint}")
    private String checkpoint;

    @Value("${azure.eventhub.connection-string}")
    private String eventHubConnectionString;

    @Value("${azure.eventhub.eventhub-name}")
    private String eventHubName;

    //private final ObjectMapper objectMapper = new ObjectMapper();


    public void consumeEventsUsingProcessor() {
        BlobContainerAsyncClient blobContainerAsyncClient = new BlobContainerClientBuilder()
                .connectionString(storageConnectionString)
                .containerName(checkpoint)
                .buildAsyncClient();
        EventProcessorClientBuilder eventProcessorClientBuilder = new EventProcessorClientBuilder()
                .connectionString(eventHubConnectionString, eventHubName)
                .consumerGroup(EventHubClientBuilder.DEFAULT_CONSUMER_GROUP_NAME)
                .processEvent(partitionProcessor)
                .processError(errorHandler)
                .checkpointStore(new BlobCheckpointStore(blobContainerAsyncClient));

        EventProcessorClient eventProcessorClient = eventProcessorClientBuilder.buildEventProcessorClient();
        log.info("Strating event processor");
        eventProcessorClient.start();
    }

    public Consumer<EventContext> partitionProcessor = eventContext -> {
        PartitionContext partitionContext = eventContext.getPartitionContext();
        EventData eventData = eventContext.getEventData();
//        log.info("Partition ID:{}", partitionContext.getPartitionId());
//        log.info("Data Offset:{}",eventData.getOffset());
//        log.info("Sequence Number:{}", eventData.getSequenceNumber());
//        log.info("Partition Key :{}", eventData.getPartitionKey());
//
//        byte[] encodedString = eventData.getBody();
//        byte[] decodeString = Base64.getDecoder().decode(encodedString);
//        String s = new String(decodeString, StandardCharsets.UTF_8);
//        try {
//            Order order = objectMapper.readValue(s, Order.class);
//            log.info("the content is:{}", objectMapper.writeValueAsString(order));
//        } catch (JsonProcessingException e) {
//            log.error("error message is:{}", e.getMessage());
//        }

        log.info("Processing event from partition {} with sequence number {} with body: {}",
                partitionContext.getPartitionId(), eventData.getSequenceNumber(),
                eventData.getBodyAsString());
        long start = System.currentTimeMillis();
        log.info("Step1 start time ms: {}", start);
        long end = System.currentTimeMillis();
       log.info("the data was: {}",eventData.getBodyAsString());
        log.info("Step2 end time ms: {}", start);
        if (eventData.getSequenceNumber() % 10 == 0) {
         //   eventContext.updateCheckpoint();
        }
        //eventContext.updateCheckpoint();
    };

    public Consumer<ErrorContext> errorHandler=errorContext -> {
        log.error("Error Occurred in partition processor for partition {}, {}",
                errorContext.getPartitionContext().getPartitionId(),
                errorContext.getThrowable());
    };

}
