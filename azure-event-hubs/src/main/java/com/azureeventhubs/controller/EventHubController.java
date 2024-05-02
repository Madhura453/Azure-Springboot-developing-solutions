package com.azureeventhubs.controller;

import com.azureeventhubs.consumer.ConsumeEventsByEventHubConsumerClient;
import com.azureeventhubs.consumer.EventHubsEventsConsumer;
import com.azureeventhubs.consumer.GenericConsumer;
import com.azureeventhubs.model.Order;
import com.azureeventhubs.producer.AzureEventHubPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/eventHub")
public class EventHubController {
    @Autowired
    private AzureEventHubPublisher azureEventHubPublisher;

    @Autowired
    private EventHubsEventsConsumer eventsConsumer;

    @Autowired
    private ConsumeEventsByEventHubConsumerClient eventHubConsumerClient;

    @Autowired
    private GenericConsumer genericConsumer;

    @PostMapping(value = "/publish", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> eventHubPublisher(
            @RequestBody List<Order> orderList) {
        azureEventHubPublisher.publishEvents(orderList);
        return new ResponseEntity<>("Successfully send events to eventhub", HttpStatus.OK);
    }

    @PostMapping(value = "/publishEventsThoughMultiplePartitions", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> publishEventsThoughMultiplePartitions(
            @RequestBody List<Order> orderList) {
        azureEventHubPublisher.publishEventsThoughMultiplePartitions(orderList);
        return new ResponseEntity<>("Successfully send events to eventhub", HttpStatus.OK);
    }

    @GetMapping(value = "/consume-processor")
    public void consumeEventsUsingProcessor() {

        eventsConsumer.consumeEventsUsingProcessor();
    }

    @GetMapping("/consume-events")
    public List<Order> consumeEventsByEventHubConsumer() {
        return eventHubConsumerClient.consumeEventFromEventhub();
    }

    @GetMapping("/consume-events-particularPartition")
    public List<Order> consumeEventsFromParticularPartition() {
        return eventHubConsumerClient.consumeEventsFromParticularPartition();
    }

    @GetMapping("/consume-events-generic")
    public void genericConsumingEvents() {
         genericConsumer.generalConsumingEvents();
    }


}
