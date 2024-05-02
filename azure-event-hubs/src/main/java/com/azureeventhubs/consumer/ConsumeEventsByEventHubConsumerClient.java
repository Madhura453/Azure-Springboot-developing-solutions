package com.azureeventhubs.consumer;

import com.azure.core.util.IterableStream;
import com.azure.messaging.eventhubs.EventHubClientBuilder;
import com.azure.messaging.eventhubs.EventHubConsumerAsyncClient;
import com.azure.messaging.eventhubs.EventHubConsumerClient;
import com.azure.messaging.eventhubs.models.EventPosition;
import com.azure.messaging.eventhubs.models.PartitionContext;
import com.azure.messaging.eventhubs.models.PartitionEvent;
import com.azure.messaging.eventhubs.models.ReceiveOptions;
import com.azureeventhubs.model.Order;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ConsumeEventsByEventHubConsumerClient {

    @Autowired
    EventHubConsumerAsyncClient eventHubConsumerClient;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<Order> consumeEventFromEventhub() {
        // this method pending because orderList returning null.
        // plesse complete
        String methodName = "consumeEventFromEventhub";
        List<Order> orderList = new ArrayList<>();
      // Flux<PartitionEvent> partitionContextFlux= eventHubConsumerClient.receive();
        eventHubConsumerClient.receive().
                flatMap(partitionEvent -> {
                    log.info("Partition ID:{}", partitionEvent.getPartitionContext().getPartitionId());
                    log.info("Data Offset:{}", partitionEvent.getData().getOffset());
                    log.info("Sequence Number:{}", partitionEvent.getData().getSequenceNumber());
                    log.info("Partition Key :{}", partitionEvent.getData().getPartitionKey());
//                    byte[] encodedString = partitionEvent.getData().getBody();
//                    byte[] decodeString = Base64.getDecoder().decode(encodedString);
                  //  String s = new String(decodeString, StandardCharsets.UTF_8);
                  //  log.info("the content is:{}", s);
                    String s =partitionEvent.getData().getBodyAsString();
                    log.info("the content is:{}", s);
                    try {
                        Order order = objectMapper.readValue(s, Order.class);
                        orderList.add(order);
                    } catch (JsonProcessingException e) {
                        log.error("error message is:{}", e.getMessage());
                    }
                    return Mono.empty();
                })
                .subscribe(message -> log.info("MethodName : {} | Message processed", methodName),
                        error -> log.info("MethodName : {} | Error occurred: ", methodName, error),
                        () -> log.info("MethodName : {} | Receiving complete", methodName))
               ;


        return orderList;

    }

    public List<Order> consumeEventsFromParticularPartition() {
//        Instant twentyFourHoursAgo = Instant.now().minus(Duration.ofHours(12));
//        EventPosition startingPosition = EventPosition.fromEnqueuedTime(twentyFourHoursAgo);
        String methodName = "consumeEventsFromParticularPartition";
        EventPosition eventPosition=EventPosition.earliest();
      String partionId=eventHubConsumerClient.getPartitionIds().blockFirst();
        List<Order> orderList = new ArrayList<>();
       eventHubConsumerClient.receiveFromPartition(partionId,eventPosition).flatMap(partitionEvent -> {
                   log.info("Partition ID:{}", partitionEvent.getPartitionContext().getPartitionId());
                   log.info("Data Offset:{}", partitionEvent.getData().getOffset());
                   log.info("Sequence Number:{}", partitionEvent.getData().getSequenceNumber());
                   log.info("Partition Key :{}", partitionEvent.getData().getPartitionKey());
                   byte[] encodedString = partitionEvent.getData().getBody();
                   byte[] decodeString = Base64.getDecoder().decode(encodedString);
                   String s = new String(decodeString, StandardCharsets.UTF_8);
                   log.info("the content is:{}", s);
                   try {
                       Order order = objectMapper.readValue(s, Order.class);
                       orderList.add(order);
                   } catch (JsonProcessingException e) {
                       log.error("error message is:{}", e.getMessage());
                   }
                   return Mono.empty();
               })
               .subscribe(message -> log.info("MethodName : {} | Message processed", methodName),
                       error -> log.info("MethodName : {} | Error occurred: ", methodName, error),
                       () -> log.info("MethodName : {} | Receiving complete", methodName))
       ;


        return orderList;
    }
}
