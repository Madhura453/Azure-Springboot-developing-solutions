package com.azure.eventgrid.config;

import com.azure.messaging.servicebus.models.ServiceBusReceiveMode;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

@Validated
@ConfigurationProperties("azure.servicebus")
@Data
public class ServiceBusProperties {
    @NotEmpty
    private String connectionString;

    private String  eventgridQueue;

    private String internalQueue;

    private ServiceBusReceiveMode subscriptionReceiveMode;
}
