package queuetrigger.read.message.object;

import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;
import com.microsoft.azure.servicebus.Message;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Azure Functions with Azure servicebus Queue trigger.
 */
public class ServiceBusQueueTriggerForReadMessageObject {
    /**
     * This function will be invoked when a new message is received at the specified path. The message contents are provided as input to this function.
     */
    @FunctionName("ServiceBusQueueTriggerForReadMessageObject")
    public void serviceBusQueueTriggerForReadMessageObject(
        @ServiceBusQueueTrigger(name = "message", queueName = "message", connection = "ServiceBusQueueConnectionString") Message message,
        final ExecutionContext context
    ) {
        context.getLogger().info("received message id: " + message.getMessageId());
        context.getLogger().info("received message contentType: " + message.getContentType());
        List<byte[]> bi=message.getMessageBody().getBinaryData();

        context.getLogger().info("received message body: " + b.length);
        context.getLogger().info("Java Queue trigger function processed a message: " + message);
    }
}
