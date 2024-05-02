package com.azuresendeventstocustomEventGridtopic.config;

import com.azure.core.credential.AzureKeyCredential;
import com.azure.messaging.eventgrid.EventGridEvent;
import com.azure.messaging.eventgrid.EventGridPublisherAsyncClient;
import com.azure.messaging.eventgrid.EventGridPublisherClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class EventGridPublisherClientConfig {

    @Value("${azure.custom.event-grid.topic.endpoint}")
    private String eventGridTopicEndpoint;

    @Value("${azure.custom.event-grid.topic.key}")
    private String eventGridTopicKey;

    @Bean
    public  EventGridPublisherAsyncClient<EventGridEvent> sendEventGridPublisherClient()
    {
        EventGridPublisherAsyncClient<EventGridEvent> eventGridEventPublisherClient = new EventGridPublisherClientBuilder()
                .endpoint(eventGridTopicEndpoint)  // make sure it accepts EventGridEvent
                .credential(new AzureKeyCredential(eventGridTopicKey))
                .buildEventGridEventPublisherAsyncClient();
        return eventGridEventPublisherClient;
    }


}
