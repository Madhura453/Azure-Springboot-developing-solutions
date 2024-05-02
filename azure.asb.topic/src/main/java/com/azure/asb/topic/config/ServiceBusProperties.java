package com.azure.asb.topic.config;

import com.azure.messaging.servicebus.models.ServiceBusReceiveMode;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;


@Validated
@ConfigurationProperties("azure.servicebus")
@Data
public class ServiceBusProperties {
    private String connectionString;
    private String madhuraTopic;
    private String madhuraSubscription;
    private ServiceBusReceiveMode subscriptionReceiveMode;
}
