package com.servicebusqueue.config;

import com.azure.messaging.servicebus.*;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.azure.messaging.servicebus.models.ServiceBusReceiveMode.PEEK_LOCK;
import static com.azure.messaging.servicebus.models.ServiceBusReceiveMode.RECEIVE_AND_DELETE;

@Configuration
@EnableConfigurationProperties(ServiceBusProperties.class)
public class ServiceBusConfig {
    private final ServiceBusProperties properties;


    public ServiceBusConfig(ServiceBusProperties properties) {
        this.properties = properties;
    }

    @Bean
    public ServiceBusReceiverAsyncClient queueSubscriberAync() {
        return new ServiceBusClientBuilder()
                .connectionString(properties.getConnectionString())
                .receiver()
                .disableAutoComplete()
                .queueName(properties.getQueueName())
                .buildAsyncClient();
    }

    @Bean
    public ServiceBusReceiverClient queueSubscriber() {
        return new ServiceBusClientBuilder()
                .connectionString(properties.getConnectionString())
                .receiver()
                .disableAutoComplete()
                .queueName(properties.getQueueName())
                .buildClient();
    }

    @Bean
    public ServiceBusSenderAsyncClient senderAynClient()
    {
        return new ServiceBusClientBuilder()
                .connectionString(properties.getConnectionString())
                .sender()
                .queueName(properties.getQueueName())
                .buildAsyncClient();
    }

    @Bean
    public ServiceBusSenderClient senderClient()
    {
        return new ServiceBusClientBuilder()
                .connectionString(properties.getConnectionString())
                .sender()
                .queueName(properties.getQueueName())
                .buildClient();
    }

    @Bean
    public ServiceBusReceiverAsyncClient peekQueueSubscriberAsync() {
        return new ServiceBusClientBuilder()
                .connectionString(properties.getConnectionString())
                .receiver()
                .disableAutoComplete()
                .receiveMode(
                        properties.getSubscriptionReceiveMode() == null
                                ? PEEK_LOCK
                                : properties.getSubscriptionReceiveMode())
                .queueName(properties.getQueueName())
                .buildAsyncClient();
    }

    @Bean
    public ServiceBusReceiverClient peekQueueSubscriber() {
        return new ServiceBusClientBuilder()
                .connectionString(properties.getConnectionString())
                .receiver()
                .disableAutoComplete()
                .receiveMode(
                        properties.getSubscriptionReceiveMode() == null
                                ? PEEK_LOCK
                                : properties.getSubscriptionReceiveMode())
                .queueName(properties.getQueueName())
                .buildClient();
    }


    @Bean
    public ServiceBusReceiverClient deadLetterClient()
    {
        String queueName=properties.getQueueName()+"/$DeadLetterQueue";
        return new ServiceBusClientBuilder()
                .connectionString(properties.getConnectionString())
                .receiver()
                .receiveMode(RECEIVE_AND_DELETE)
                .queueName(queueName)
                .buildClient();
    }

}
