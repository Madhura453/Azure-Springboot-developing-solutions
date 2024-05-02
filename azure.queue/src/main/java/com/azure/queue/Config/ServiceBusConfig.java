package com.azure.queue.Config;

import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusReceiverAsyncClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ServiceBusProperties.class)
public class ServiceBusConfig {
    private final ServiceBusProperties properties;


    public ServiceBusConfig(ServiceBusProperties properties) {
        this.properties = properties;
    }

    @Bean
    public ServiceBusReceiverAsyncClient rpaQueueSubscriber() {
        return new ServiceBusClientBuilder()
                .connectionString(properties.getConnectionString())
                .receiver()
                .disableAutoComplete()
                .queueName(properties.getMqueue())
                .buildAsyncClient();
    }

}
