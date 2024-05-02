package org.queuetrigger.toread.blobinput;

import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * Azure Functions with Azure Storage Queue trigger.
 */
public class QueueTriggerToReadBlobInput {
    /**
     * This function will be invoked when a new message is received at the specified path. The message contents are provided as input to this function.
     */
    @FunctionName("QueueTrigger-Java")
    public void queueTriggerToReadBlobInput(
        @QueueTrigger(name = "message", queueName = "messages", connection = "StorageConnection") String filename,
        @BlobInput(name = "file",dataType = "binary",path = "data/{queueTrigger}") byte[] content,
        final ExecutionContext context
    ) {
        context.getLogger().info("The size of \"" + filename + "\" is: " + content.length + " bytes");

        context.getLogger().info("The upload text is:"+new String(content, StandardCharsets.UTF_8));
    }



//    @FunctionName("getBlobSizeHttp")
//    public HttpResponseMessage blobSize(
//            @HttpTrigger(name = "req",
//                    methods = {HttpMethod.GET},
//                    authLevel = AuthorizationLevel.ANONYMOUS)
//            HttpRequestMessage<Optional<String>> request,
//            @BlobInput(
//                    name = "file",
//                    dataType = "binary",
//                    path = "data/{Query.file}",connection = "StorageConnection")
//            byte[] content,
//            final ExecutionContext context) {
//        // build HTTP response with size of requested blob
//        return request.createResponseBuilder(HttpStatus.OK)
//                .body("The size of \"" + request.getQueryParameters().get("file") + "\" is: " + content.length + " bytes")
//                .build();
//    }


}



/*
microsoft document for this

https://learn.microsoft.com/en-us/azure/azure-functions/functions-bindings-storage-blob-input?tabs=in-process%2Cextensionv5&pivots=programming-language-java
 */