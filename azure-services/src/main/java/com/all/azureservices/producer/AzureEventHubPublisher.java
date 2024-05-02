package com.all.azureservices.producer;//package com.java.azureservices.producer;

import com.azure.messaging.eventhubs.EventData;
import com.azure.messaging.eventhubs.EventDataBatch;
import com.azure.messaging.eventhubs.EventHubClientBuilder;
import com.azure.messaging.eventhubs.EventHubProducerClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class AzureEventHubPublisher {
    @Value("${azure.eventhub.connection-string}")
    private String connectionString;

    @Value("${azure.eventhub.eventhub-name}")
    private String eventHubName;

    public void publishEvents(List<String> stringList) {
        EventHubProducerClient producer = new EventHubClientBuilder()
                .connectionString(connectionString, eventHubName)
                .buildProducerClient();
        List<EventData> allEvents = stringList.stream().map(s -> new EventData(s)).collect(Collectors.toList());
        EventDataBatch eventDataBatch = producer.createBatch();
        allEvents.stream().forEach(eventData ->
                {
                    if (!eventDataBatch.tryAdd(eventData)) {
                     producer.send(eventDataBatch);
                    }
                    if (!eventDataBatch.tryAdd(eventData)) {
                        throw new IllegalArgumentException("Event is too large for an empty batch. Max size: "+
                                eventDataBatch.getMaxSizeInBytes());
                    }
                }

        );
        if (eventDataBatch.getCount() > 0) {
            producer.send(eventDataBatch);
        }
        producer.close();
    }


}
