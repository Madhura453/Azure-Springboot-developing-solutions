package com.azure.eventgrid.config;

import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusReceiverAsyncClient;
import com.azure.messaging.servicebus.ServiceBusSenderAsyncClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.azure.messaging.servicebus.models.ServiceBusReceiveMode.PEEK_LOCK;

@Configuration
@EnableConfigurationProperties(ServiceBusProperties.class)
public class ServiceBusConfig {

    private final ServiceBusProperties properties;

    public ServiceBusConfig(ServiceBusProperties properties) {
        this.properties = properties;
    }


    @Bean
    public ServiceBusReceiverAsyncClient eventGridReceiver() {
        return new ServiceBusClientBuilder()
                .connectionString(properties.getConnectionString())
                .receiver()
                .disableAutoComplete()
                .receiveMode(
                        properties.getSubscriptionReceiveMode() == null
                                ? PEEK_LOCK
                                : properties.getSubscriptionReceiveMode())
                .queueName(properties.getEventgridQueue())
                .buildAsyncClient();
    }

    @Bean
    public ServiceBusReceiverAsyncClient internalReceiver() {
        return new ServiceBusClientBuilder()
                .connectionString(properties.getConnectionString())
                .receiver()
                .disableAutoComplete()
                .receiveMode(
                        properties.getSubscriptionReceiveMode() == null
                                ? PEEK_LOCK
                                : properties.getSubscriptionReceiveMode())
                .queueName(properties.getInternalQueue())
                .buildAsyncClient();
    }

    @Bean
    public ServiceBusSenderAsyncClient internalSender() {
        return new ServiceBusClientBuilder()
                .connectionString(properties.getConnectionString())
                .sender()
                .queueName(properties.getInternalQueue())
                .buildAsyncClient();
    }


}
