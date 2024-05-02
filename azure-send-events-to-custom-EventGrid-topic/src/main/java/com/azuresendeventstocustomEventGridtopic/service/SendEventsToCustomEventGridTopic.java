package com.azuresendeventstocustomEventGridtopic.service;

import com.azure.core.util.BinaryData;
import com.azure.messaging.eventgrid.EventGridEvent;
import com.azure.messaging.eventgrid.EventGridPublisherAsyncClient;
import com.azuresendeventstocustomEventGridtopic.dto.Order;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SendEventsToCustomEventGridTopic {

    @Autowired
    EventGridPublisherAsyncClient<EventGridEvent> sendEventGridPublisherClient;

    public void SendEventsToCustomEventGridTopic(List<Order> orderList)
    {
        List<EventGridEvent> eventGridEvents=convertingToEventGridEventList(orderList);
        sendEventGridPublisherClient.sendEvents(eventGridEvents).block();
    }

   public List<EventGridEvent> convertingToEventGridEventList(List<Order> orderList)
   {
       try {
           log.info("The list of event grid events is :{}",new ObjectMapper().writeValueAsString(orderList));
       } catch (JsonProcessingException e) {
           throw new RuntimeException(e);
       }
       return orderList.stream().map(order -> new EventGridEvent("/EventGridEvents/example/source",
                 "Example.EventType", BinaryData.fromObject(order), "0.1")).collect(Collectors.toList());
   }

}
