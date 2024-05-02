package org.eventhubtrigger.consumingevents;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.OutputBinding;
import com.microsoft.azure.functions.annotation.*;

import java.time.ZonedDateTime;
import java.util.Map;

/**
 * Azure Functions with Event Hub trigger.
 * and Blob Output using date in path along with message partition ID
 * and message sequence number from EventHub Trigger Properties
 */
public class EventHubTriggerWithBlobOutput {

    @FunctionName("EventHubTriggerWithBlobOutput")
    public void eventHubTriggerWithBlobOutput(
            @EventHubTrigger(name="message",eventHubName = "general",consumerGroup = "$Default",connection = "EventhubConnection",cardinality = Cardinality.ONE) String message,
            final ExecutionContext context,
            @BindingName("Properties") Map<String, Object> properties,
            @BindingName("PartitionContext") Map<String, Object> partitionContext
            )
    {
        //var et = ZonedDateTime.parse(enqueuedTimeUtc + "Z"); // needed as the UTC time presented does not have a TZ
        // indicator
        context.getLogger().info("EventHub message received:"+message);
        context.getLogger().info("Properties: " + properties);
      //  context.getLogger().info("System Properties: " + systemProperties);
        context.getLogger().info("partitionContext: " + partitionContext);
        context.getLogger().info("conteent is"+message);
//        context.getLogger().info("EnqueuedTimeUtc: " + et);

    }
}
