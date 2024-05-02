package com.all.azureservices.controller;

import com.all.azureservices.consumer.EventHubsEventsConsumer;
import com.all.azureservices.producer.AzureEventHubPublisher;
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

    @GetMapping(value = "/publish", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> eventHubPublisher(
            @RequestBody ArrayList<String> stringList) {
        azureEventHubPublisher.publishEvents(stringList);
        return new ResponseEntity<>("Successfully send events to eventhub", HttpStatus.OK);
    }


}
