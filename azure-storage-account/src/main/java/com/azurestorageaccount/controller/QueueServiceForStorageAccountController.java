package com.azurestorageaccount.controller;

import com.azurestorageaccount.service.QueueServiceForStorageAccountOne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class QueueServiceForStorageAccountController {

    @Autowired
    private QueueServiceForStorageAccountOne queueServiceForStorageAccountOne;

    @PostMapping("/send-messages")
    public void sendMessagesToQueue(@RequestBody List<String> messages)
    {
        queueServiceForStorageAccountOne.sendMessagesToQueue(messages)
                ;
    }

    @GetMapping("/peek-message")
    public List<String> peekMessagesFromQueue()
    {
        return queueServiceForStorageAccountOne.peekMessagesFromQueue();
    }

    @GetMapping("/receive-message")
    public List<String> receivedMessageFromQueue()
    {
        return queueServiceForStorageAccountOne.receivedMessageFromQueue();
    }
}
