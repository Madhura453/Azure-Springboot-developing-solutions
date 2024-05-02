package org.azure.blobtrigger.to.cosmosdb.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Blob {
    public String blobId;

    public int sizeOfBlob;

    public String nameOfBlob;

    public String contentOfBlob;
}
