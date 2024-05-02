package org.azure.blobtrigger.to.cosmosdb;

import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;
import org.azure.blobtrigger.to.cosmosdb.Model.Blob;

import java.nio.charset.StandardCharsets;

/**
 * Azure Functions with Azure Blob trigger.
 */
public class BlobTriggerToCosmosDB {
    /**
     * This function will be invoked when a new or updated blob is detected at the specified path. The blob contents are provided as input to this function.
     */
    @FunctionName("BlobTrigger-Java")
    public void blobTriggerToCosmosDB(
            @BlobTrigger(name = "content", path = "data/{name}", dataType = "binary",connection = "StorageConnection") byte[] content,
            @BindingName("name") String name,
            @CosmosDBOutput(name = "cosmosdb",databaseName = "cosmostrigger",collectionName = "blob",connectionStringSetting = "cosmosdbConnectionString") OutputBinding<Blob> outBlob,
            final ExecutionContext context
    ) {
        context.getLogger().info("Java Blob trigger function processed a blob. Name: " + name + "\n  Size: " + content.length + " Bytes");
        Blob blob=new Blob();
        blob.setBlobId("01");
        blob.setSizeOfBlob(content.length);
        blob.setNameOfBlob(name);
        String result=new String(content, StandardCharsets.UTF_8);
        blob.setContentOfBlob(result);
        outBlob.setValue(blob);
    }


}
