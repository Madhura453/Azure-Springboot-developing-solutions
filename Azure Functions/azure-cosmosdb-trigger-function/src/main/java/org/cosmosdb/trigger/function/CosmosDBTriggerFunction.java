package org.cosmosdb.trigger.function;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.annotation.CosmosDBTrigger;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.cosmosdb.trigger.function.Model.Activity;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * Azure Functions with HTTP Trigger.
 */
public class CosmosDBTriggerFunction {
    /**
     * This function listens at endpoint "/api/HttpExample". Two ways to invoke it using "curl" command in bash:
     * 1. curl -d "HTTP Body" {your host}/api/HttpExample
     * 2. curl "{your host}/api/HttpExample?name=HTTP%20Query"
     */
    @FunctionName("cosmosDBMonitor")
    public void cosmosDBProcessor(
            @CosmosDBTrigger(
                name = "items",
                databaseName ="madhura",
                collectionName = "jsonfilecontainer",
                createLeaseCollectionIfNotExists = true,
                connectionStringSetting = "AzureCosmosDbConnection") Activity[] items,
            final ExecutionContext context) {
        context.getLogger().info("Java Cosmos DB trigger processing--");

        Arrays.stream(items).forEach(item ->
        {
            try {
                context.getLogger().info("the item was{}"+new ObjectMapper().writeValueAsString(item));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });

        context.getLogger().info(items.length+" item(s) is/are changed");
    }
}

