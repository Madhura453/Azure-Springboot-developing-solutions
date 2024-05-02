package com.cosmosdbtable.config;

import com.azure.data.tables.TableClient;
import com.azure.data.tables.TableClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TableServiceConfiguration {

    @Value("${azure.cosmos.table.connection-string}")
    private String connectionString;

    @Value("${azure.cosmos.table.name}")
    private String tableName;

    @Bean
    public TableClient tableClient()
    {
        return new TableClientBuilder()
                .connectionString(connectionString)
                .tableName(tableName)
                .buildClient();
    }
}
