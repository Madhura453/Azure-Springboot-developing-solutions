package org.servicebus.queue.trigger;

import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;

/**
 * Azure Functions with Azure Storage Queue trigger.
 */
public class ServiceBusQueueTriggerForReadMessage {
    /**
     * This function will be invoked when a new message is received at the specified path. The message contents are provided as input to this function.
     */
    @FunctionName("QueueTrigger-read-message")
    public void serviceBusQueueTriggerForReadMessage(
        @ServiceBusQueueTrigger(name = "message", queueName = "message", connection = "ServiceBusConnectionString") String message,
        final ExecutionContext context
    ) {
        context.getLogger().info("Java Queue trigger function processed a message: " + message);
    }
}
