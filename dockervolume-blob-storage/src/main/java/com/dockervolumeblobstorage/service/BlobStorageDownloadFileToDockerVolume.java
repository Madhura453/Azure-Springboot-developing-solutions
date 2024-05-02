package com.dockervolumeblobstorage.service;

import com.azure.storage.blob.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

@Slf4j
@Service
public class BlobStorageDownloadFileToDockerVolume {

    private static String secretpath = "/app/secrets/storage-connection";

    File file=new File(secretpath);

//    private String connectionString;
//    =  new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);

    @Value("${azure.storage.blob-container}")
    private String blobContainer;

    private String local_blob="/app/data/madhuratext.txt";

    public String blobStorageDownloadFileToDockerVolume()
    {
        String connectionString = null;
        try {
            connectionString = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
