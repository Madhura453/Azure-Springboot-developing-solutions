package com.azure.asb.topic.config;

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
    public ServiceBusReceiverAsyncClient topicSubscriberAsync() {
        return new ServiceBusClientBuilder()
                .connectionString(properties.getConnectionString())
                .receiver()
                .disableAutoComplete()
                .receiveMode(
                        properties.getSubscriptionReceiveMode() == null
                                ? PEEK_LOCK
                                : properties.getSubscriptionReceiveMode())
                .topicName(properties.getMadhuraTopic())
                .subscriptionName(properties.getMadhuraSubscription())
                .buildAsyncClient();
    }


    @Bean
    public ServiceBusReceiverClient topicSubscriber() {
        return new ServiceBusClientBuilder()
                .connectionString(properties.getConnectionString())
                .receiver()
                .disableAutoComplete()
                .receiveMode(RECEIVE_AND_DELETE)
                .topicName(properties.getMadhuraTopic())
                .subscriptionName(properties.getMadhuraSubscription())
                .buildClient();
    }


    @Bean
    public ServiceBusSenderAsyncClient senderClientAsync() {
        return new ServiceBusClientBuilder()
                .connectionString(properties.getConnectionString())
                .sender()
                .topicName(properties.getMadhuraTopic())
                .buildAsyncClient();
    }

    @Bean
    public ServiceBusSenderClient senderClient() {
        return new ServiceBusClientBuilder()
                .connectionString(properties.getConnectionString())
                .sender()
                .topicName(properties.getMadhuraTopic())
                .buildClient();
    }
}
