package com.dockervolumeblobstorage.service;

import com.azure.storage.blob.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BlobStorageDownloadFileToDockerVolume {
    @Value("${azure.storage.account-connection-string}")
    private String connectionString;

    @Value("${azure.storage.blob-container}")
    private String blobContainer;

    private String local_blob="/app/data/madhuratext.txt";
    // this is the volume in container in docker

   public String blobStorageDownloadFileToDockerVolume()
    {
        String methodName = "blobStorageDownloadFileToDockerVolume";
        String fileName="input.txt";
        try {
            log.info("MethodName : {} | Establishing connection with container", methodName);
            BlobServiceClient blobServiceClient =
                    new BlobServiceClientBuilder().connectionString(connectionString).buildClient();
            log.info(
                    "MethodName : {} | Establishing connection with blob storage: {}",
                    methodName,
                    blobContainer);
            BlobContainerClient srcContClient =
                    blobServiceClient.getBlobContainerClient(blobContainer);
            log.info("MethodName : {} | Establishing connection with file: {}", methodName, fileName);

            BlobClient blob_client = srcContClient.getBlobClient(fileName);
            blob_client.downloadToFile(local_blob);
            log.info("file downloaded successfully");

        } catch (Exception e) {
            log.error("MethodName : {} | Error: {}", methodName, ExceptionUtils.getStackTrace(e));
        }

        return "success";
    }
}
