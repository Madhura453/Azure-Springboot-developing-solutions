package org.eventgridtrigger.function;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;
import org.eventgridtrigger.function.dto.EventGridEvent;

/**
 * Azure Functions with Azure Blob trigger.
 */
public class EventGridTriggerFunction {
    /**
     * This function will be invoked when a new or updated blob is detected at the specified path. The blob contents are provided as input to this function.
     */
    @FunctionName("EventGridTriggerFunction")
    public void eventGridTriggerFunction(
            @EventGridTrigger(name = "event") EventGridEvent eventGridEvent,
            final ExecutionContext context
    ) {
        context.getLogger().info("Java Event Hub trigger function executed.");
        try {
            context.getLogger().info("content of eventGridEvent is:"+new ObjectMapper()
                    .writeValueAsString(eventGridEvent));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
