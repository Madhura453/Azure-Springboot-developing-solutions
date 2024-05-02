package com.azure.cosmosdb.config;

import com.azure.cosmos.*;
import com.azure.spring.data.cosmos.config.AbstractCosmosConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class CosmosRepoConfig extends AbstractCosmosConfiguration {

    @Value("${azure.cosmos.url}")
    private String uri;

    @Value("${azure.cosmos.key}")
    private String cosmosPrimaryKey;

    @Value("${azure.cosmos.database}")
    private String databaseName;


    @Bean
    public CosmosClientBuilder getCosmosClientBuilder()
    {
        DirectConnectionConfig directConnectionConfig = new DirectConnectionConfig();
        GatewayConnectionConfig gatewayConnectionConfig = new GatewayConnectionConfig();
        return new CosmosClientBuilder()
                .endpoint(uri)
                .key(cosmosPrimaryKey)
                .contentResponseOnWriteEnabled(true)
                .consistencyLevel(ConsistencyLevel.SESSION)
                .directMode(directConnectionConfig,gatewayConnectionConfig);

    }

    @Bean
    public CosmosDatabase database()
    {
        return getCosmosClientBuilder().buildClient().getDatabase(databaseName);
    }


    @Bean
    public CosmosContainer container()
    {
        return database().getContainer("normal");
    }

    @Bean
    public CosmosContainer embeddedContainer()
    {
        return database().getContainer("embeddedData");
    }

    @Bean
    public CosmosContainer jsonFileContainer()
    {
        return database().getContainer("jsonfilecontainer");
    }



    @Override
    protected String getDatabaseName() {
        return databaseName;
    }


}
