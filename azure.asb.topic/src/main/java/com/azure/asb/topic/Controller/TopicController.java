package com.azure.asb.topic.Controller;

import com.azure.asb.topic.listner.MultipleMessageListener;
import com.azure.asb.topic.listner.TopicListner;
import com.azure.asb.topic.model.Order;
import com.azure.asb.topic.publisher.SendMessageToTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topic")
public class TopicController {
    private static final Logger logger = LoggerFactory.getLogger(TopicController.class);

    @Autowired
    private SendMessageToTopic sendTopicMessage;

    @Autowired
    private TopicListner topicListner;

    @Autowired
    private MultipleMessageListener multipleMessageListener;

    @PostMapping("/single-message")
    public void sendMessageToTopic(@RequestBody Order order) {
        sendTopicMessage.sendSingleTopicMessage(order);
    }

    @PostMapping("/multiple-messages")
    public void sendTopicMessage(@RequestBody List<Order> orderList) {
        sendTopicMessage.sendMultipleMessagesToTopicWithDefault(orderList);
    }


    @PostMapping("/multiple-messages-filter")
    public void sendMultipleMessagesFilter(@RequestBody List<Order> orderList) {
        sendTopicMessage.sendMultipleMessagesToTopicFilterExample(orderList);
    }


    @GetMapping("/receive-multiple-messages")
    public List<Order> receiveMultipleMessages()
    {
        return multipleMessageListener.multipleMessageListener();
    }

    @GetMapping("//receive-message")
    public void receiveSingleMessage()
    {
        topicListner.receiveSubscriptionMessage();
    }





//
//    @PostMapping("/messages")
//    public void postMessage(@RequestParam String message) {
//       sendTopicMessage.postMessage(message);
//    }



}
