package org.topic.trigger.read.message.object;

import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;
import com.microsoft.azure.servicebus.Message;

/**
 * Azure Functions with Azure Storage Queue trigger.
 */
public class TopicTriggerToReadMessageObject {
    /**
     * This function will be invoked when a new message is received at the specified path. The message contents are provided as input to this function.
     */
    @FunctionName("TopicTriggerToReadMessageObject")
    public void topicTriggerToReadMessageObject(
        @ServiceBusTopicTrigger(name = "message", topicName = "madhu",subscriptionName = "filter",connection = "ServiceBusTopicConnectionAppSetting") Message message,
        final ExecutionContext context
    ) {
        context.getLogger().info("Java serviceBus topic trigger function processed a message: " + message);
    }
}
