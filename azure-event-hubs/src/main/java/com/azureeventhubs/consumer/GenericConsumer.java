package com.azureeventhubs.consumer;

import com.azure.messaging.eventhubs.EventHubConsumerAsyncClient;
import com.azureeventhubs.model.Order;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class GenericConsumer {

    @Autowired
    EventHubConsumerAsyncClient eventHubConsumerClient;

    public void generalConsumingEvents()
    {
        String methodName="generalConsumingEvents";
        eventHubConsumerClient.receive().
                flatMap(partitionEvent -> {
                    log.info("Partition ID:{}", partitionEvent.getPartitionContext().getPartitionId());
                    log.info("Data Offset:{}", partitionEvent.getData().getOffset());
                    log.info("Sequence Number:{}", partitionEvent.getData().getSequenceNumber());
                    log.info("Partition Key :{}", partitionEvent.getData().getPartitionKey());
                    String s =partitionEvent.getData().getBodyAsString();
                    log.info("the content is:{}", s);
                    return Mono.empty();
                })
                .subscribe(message -> log.info("MethodName : {} | Message processed", methodName),
                        error -> log.info("MethodName : {} | Error occurred: ", methodName, error),
                        () -> log.info("MethodName : {} | Receiving complete", methodName))
        ;

    }
}
