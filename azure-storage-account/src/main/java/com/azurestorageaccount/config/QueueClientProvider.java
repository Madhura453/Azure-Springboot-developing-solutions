package com.azurestorageaccount.config;

import com.azure.storage.queue.QueueAsyncClient;
import com.azure.storage.queue.QueueClient;
import com.azure.storage.queue.QueueClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueClientProvider {


    @Value("${azure.storage.account-connection-string}")
    private String storageConnectionString;

    @Value("${azure.storage.queue.name}")
    private String queueName;

    @Bean
    public QueueClient storageQueueClient()
    {
       return new QueueClientBuilder()
                .connectionString(storageConnectionString)
                .queueName(queueName)
                .buildClient();
    }

    @Bean
    public QueueAsyncClient storageAyncQueueClient()
    {
        return new QueueClientBuilder()
                .connectionString(storageConnectionString)
                .queueName(queueName)
                .buildAsyncClient();
    }
}
