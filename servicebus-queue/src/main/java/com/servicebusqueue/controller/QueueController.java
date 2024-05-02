package com.servicebusqueue.controller;

import com.servicebusqueue.model.Order;
import com.servicebusqueue.listner.DeadLetterQueueReceiver;
import com.servicebusqueue.listner.MultipleMessagesListener;
import com.servicebusqueue.listner.QueueListener;
import com.servicebusqueue.publisher.QueueMessageProducer;
import com.servicebusqueue.service.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/queue")
public class QueueController {

    @Autowired
    private QueueService queueService;

    @Autowired
    private QueueMessageProducer queueMessageProducer;

    @Autowired
    private MultipleMessagesListener multipleMessagesListener;

    @Autowired
    QueueListener queueListener;

    @Autowired
    DeadLetterQueueReceiver deadLetterQueueReceiver;

    @PostMapping("/order")
    public void sendObjectsToQueue(@RequestBody Order order) {
        queueMessageProducer.sendQueueMessage(order);
    }

    @GetMapping("/receive-order")
    public void receiveOrder() {

        //queueListener.receiveQueueMessage();
    }

    @PostMapping("/orders")
    public void sendMultipleMessages(@RequestBody List<Order> orderList) {
        queueMessageProducer.sendMultipleMessagesWithDefault(orderList);
    }

    @GetMapping("/receive-multiple-message")
    public List<Order> receiveMultipleMessages() {

        return multipleMessagesListener.multipleMessageListener();
    }

    @GetMapping("/peek-order")
    public void peekOrder() {
        queueListener.peekQueueMessage();
    }

    @GetMapping("/peek-multiple-messages")
    public List<Order> peekMultipleMessages() {
        return multipleMessagesListener.peekMultipleMessages();
    }

    @GetMapping("/peek-multiple-messages-flatmap")
    public void peekMultipleMessagesWithFlatMap() {
        queueListener.peekQueueMessageFlatmap();
    }

    @PostMapping("/time-to-live")
    public void timeToLiveFeature(@RequestBody List<Order> orderList) {
        queueMessageProducer.timeToLiveFeature(orderList);
    }

    @GetMapping("/dead-letter-queue")
    public List<Order> deadLetterQueueReceiveMultipleMessages()
    {
        return deadLetterQueueReceiver.deadLetterQueueMessages();
    }

    @PostMapping("/duplicate-detection")
    public void duplicateDetection(@RequestBody List<Order> orderList) {
        queueMessageProducer.duplicateDetection(orderList);
    }

    @GetMapping("/getApplicationPropertiesToMessages")
    public void getApplicationPropertiesToMessages()
    {
        queueListener.getApplicationProperties();
    }

    @PostMapping("/setApplicationPropertiesToMessages")
    public void setApplicationPropertiesToMessages(@RequestBody List<Order> orderList) {
        queueMessageProducer.setApplicationPropertiesToMessages(orderList);
    }

}
