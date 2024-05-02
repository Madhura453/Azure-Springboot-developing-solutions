package com.azureeventhubs.producer;//package com.java.azureservices.producer;

import com.azure.messaging.eventhubs.EventData;
import com.azure.messaging.eventhubs.EventDataBatch;
import com.azure.messaging.eventhubs.EventHubClientBuilder;
import com.azure.messaging.eventhubs.EventHubProducerClient;
import com.azureeventhubs.model.Order;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class AzureEventHubPublisher {
    @Value("${azure.eventhub.connection-string}")
    private String connectionString;

    @Value("${azure.eventhub.eventhub-name}")
    private String eventHubName;

    @Autowired
    private EventHubProducerClient eventHubProducerClient;

    public void publishEvents(List<Order> orderListList) {
        List<EventData> allEvents = convertingToEventDataList(orderListList);
        sendEvents(allEvents);
    }


    public void publishEventsThoughMultiplePartitions(List<Order> orderListList)
    {
        List<EventData> allEvents = convertingToEventDataList(orderListList);
        EventDataBatch eventDataBatch = eventHubProducerClient.createBatch();
        for(int i=0;i<20;i++)
        {
            for (EventData eventData : allEvents) {
                // try to add the event from the array to the batch
                if (!eventDataBatch.tryAdd(eventData)) {
                    // if the batch is full, send it and then create a new batch
                    eventHubProducerClient.send(eventDataBatch);
                    eventDataBatch = eventHubProducerClient.createBatch();

                    // Try to add that event that couldn't fit before.
                    if (!eventDataBatch.tryAdd(eventData)) {
                        throw new IllegalArgumentException("Event is too large for an empty batch. Max size: "
                                + eventDataBatch.getMaxSizeInBytes());
                    }
                }
            }
            // send the last batch of remaining events
            if (eventDataBatch.getCount() > 0) {
                eventHubProducerClient.send(eventDataBatch);
            }

        }
    }

    private static List<EventData> convertingToEventDataList(List<Order> orderListList) {
        List<EventData> allEvents = orderListList.stream().
                map(order -> {
                            String bytes= "";
                            String body ="";
                            try {
                                body = new ObjectMapper().writeValueAsString(order);
                                bytes=  Base64.getEncoder().encodeToString(body.getBytes());
                            } catch (JsonProcessingException e) {
                                throw new RuntimeException(e);
                            }
                            //return new EventData(bytes);
                            return new EventData(body);
                }
                        ).collect(Collectors.toList());
        return allEvents;
    }

    private void sendEvents(List<EventData> allEvents) {
        EventDataBatch eventDataBatch = eventHubProducerClient.createBatch();

        for (EventData eventData : allEvents) {
            // try to add the event from the array to the batch
            if (!eventDataBatch.tryAdd(eventData)) {
                // if the batch is full, send it and then create a new batch
                eventHubProducerClient.send(eventDataBatch);
                eventDataBatch = eventHubProducerClient.createBatch();

                // Try to add that event that couldn't fit before.
                if (!eventDataBatch.tryAdd(eventData)) {
                    throw new IllegalArgumentException("Event is too large for an empty batch. Max size: "
                            + eventDataBatch.getMaxSizeInBytes());
                }
            }
        }
        // send the last batch of remaining events
        if (eventDataBatch.getCount() > 0) {
           eventHubProducerClient.send(eventDataBatch);
        }


        //  eventHubProducerClient.close();
    }





}
