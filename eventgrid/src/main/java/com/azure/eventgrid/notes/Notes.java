package com.azure.eventgrid.notes;

public class Notes {
    /*
    1. Event means add entity,delete entity, update entity like that
    2. This even grid is doing act as blob trigger
    3. when ever file upload in blob trigger it will send message to queue in this format.

    Contents of message as string: {
  "topic": "/subscriptions/f8635b6d-430b-424c-92c4-300774073cbe/resourceGroups/madhura/providers/Microsoft.Storage/storageAccounts/entimadhura",
  "subject": "/blobServices/default/containers/nouseofme/blobs/input.txt",
  "eventType": "Microsoft.Storage.BlobCreated",
  "id": "62d99ad0-301e-009d-140c-f8201506fc05",
  "data": {
    "api": "PutBlob",
    "clientRequestId": "faa61dc2-e5f2-4a88-8787-168fbeac4023",
    "requestId": "62d99ad0-301e-009d-140c-f82015000000",
    "eTag": "0x8DAC6239E784922",
    "contentType": "text/plain",
    "contentLength": 22,
    "blobType": "BlockBlob",
    "url": "https://entimadhura.blob.core.windows.net/nouseofme/input.txt",
    "sequencer": "000000000000000000000000000049D8000000000010d26b",
    "storageDiagnostics": {
      "batchId": "b2407df4-2006-0081-000c-f87275000000"
    }
  },
  "dataVersion": "",
  "metadataVersion": "1",
  "eventTime": "2022-11-14T09:35:51.1194669Z"
}


continue on notes 1 class

     */
}
