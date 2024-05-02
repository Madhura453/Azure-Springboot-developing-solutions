package com.azure.queue.Config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

@Validated
@ConfigurationProperties("azure.servicebus")
@Data
public class ServiceBusProperties {
    @NotEmpty
    private String connectionString;

    private String mqueue;




}
