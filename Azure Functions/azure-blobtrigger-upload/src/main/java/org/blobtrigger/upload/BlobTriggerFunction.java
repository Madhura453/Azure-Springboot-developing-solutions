package org.blobtrigger.upload;

import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.IOUtils;

/**
 * Azure Functions with Azure Blob trigger.
 */
public class BlobTriggerFunction {
    /**
     * This function will be invoked when a new or updated blob is detected at the specified path. The blob contents are provided as input to this function.
     *
     * @return
     */
    @FunctionName("BlobTrigger-Java")
    public void blobTriggerWhenUploadFile(
            @BlobTrigger(name = "content", path = "data/{name}", dataType = "binary", connection = "StorageConnection") byte[] content,
            @BindingName("name") String name,
            final ExecutionContext context
    ) {
        context.getLogger().info("Java Blob trigger function processed a blob. Name: " + name + "\n  Size: " + content.length + " Bytes");

     //   String result=new String(content,StandardCharsets.UTF_8);
                InputStream inputStream = new ByteArrayInputStream(content);
        String result = null;
        try {
            result = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        context.getLogger().info("The upload file content is: " + result);

    }
}
