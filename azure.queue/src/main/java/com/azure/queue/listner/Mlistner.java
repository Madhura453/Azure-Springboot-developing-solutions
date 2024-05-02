package com.azure.queue.listner;

import com.azure.messaging.servicebus.ServiceBusReceiverAsyncClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;



@Component
@Slf4j
public class Mlistner {
    @Autowired
    private ServiceBusReceiverAsyncClient rpaQueueSubscriber;


    public void receiveRpaMessage() {
        String methodName = "receiveRpaMessage";
        log.info("Listening to RPA Queue Started");

        rpaQueueSubscriber
                .receiveMessages()
                .flatMap(
                        message -> {
                            try {
                                String rpaMessageStr = message.getBody().toString();
                                log.info("**************Received RPA message:**************** {} ", rpaMessageStr);
                                System.out.println(rpaMessageStr);
                                return rpaQueueSubscriber.complete(message);
                            } catch (Exception ex) {
                                log.error(
                                        "MethodName : {} | Exception {} caught | Error: {}",
                                        methodName,
                                        ex.getClass(),
                                        ExceptionUtils.getStackTrace(ex));
                                return rpaQueueSubscriber.abandon(message);
                            }
                        })
                .subscribe(
                        message -> log.info("MethodName : {} | Message processed", methodName),
                        error -> log.info("MethodName : {} | Error occurred: ", methodName, error),
                        () -> log.info("MethodName : {} | Receiving complete", methodName));
    }




    @JmsListener(destination = "${madhura.queue}", containerFactory = "jmsListenerContainerFactory")
    public void receiveMessage(String poMessage) throws IllegalAccessException {
        log.info("Length of po message is " + poMessage.length());
        System.out.println(poMessage);
    }
}
