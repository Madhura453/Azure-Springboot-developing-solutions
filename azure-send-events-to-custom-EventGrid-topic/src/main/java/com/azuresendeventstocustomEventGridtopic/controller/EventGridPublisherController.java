package com.azuresendeventstocustomEventGridtopic.controller;

import com.azuresendeventstocustomEventGridtopic.dto.Order;
import com.azuresendeventstocustomEventGridtopic.service.SendEventsToCustomEventGridTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/event-grid-publish")
public class EventGridPublisherController {

    @Autowired
    private SendEventsToCustomEventGridTopic sendEventsToCustomEventGridTopic;

    @PostMapping("/publish-events")
    public void publishEventsToCustomEventGridTopic(@RequestBody List<Order> orderList)
    {
        sendEventsToCustomEventGridTopic.SendEventsToCustomEventGridTopic(orderList);
    }
}
