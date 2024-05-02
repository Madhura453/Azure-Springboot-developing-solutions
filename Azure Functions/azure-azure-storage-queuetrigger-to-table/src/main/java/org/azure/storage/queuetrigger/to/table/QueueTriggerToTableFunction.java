package org.azure.storage.queuetrigger.to.table;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;
import org.azure.storage.queuetrigger.to.table.model.Order;
import com.fasterxml.jackson.databind.ObjectMapper;


public class QueueTriggerToTableFunction {
/**
 * Azure Functions with Azure Storage Queue trigger.
 */

    @FunctionName("storage-QueueTrigger-to-table")
    public void run(
        @QueueTrigger(name = "message", queueName = "messages", connection = "StorageConnection") Order message,
        @TableOutput(name="order",tableName = "Orders",connection = "StorageConnection") OutputBinding<Order> order,
        final ExecutionContext context
    ) {
        try {
            context.getLogger().info("Java Queue trigger function processed a message: " + new ObjectMapper().writeValueAsString(message));
            context.getLogger().info("order properties: " +message.getPartitionKey()+"row"+
                    message.getRowKey()+"quan"+message.getQuantity()+"unit"+
                    message.getUnitPrice());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        order.setValue(message);
        try {
            context.getLogger().info("table order: " + new ObjectMapper().writeValueAsString(order));
            context.getLogger().info("order value: " + new ObjectMapper().writeValueAsString(order.getValue()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
