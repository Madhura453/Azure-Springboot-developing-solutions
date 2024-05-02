package com.azurestorageaccount.service;

import com.azure.core.http.rest.PagedIterable;
import com.azure.core.util.BinaryData;
import com.azure.storage.blob.*;
import com.azure.storage.blob.models.*;
import com.azure.storage.blob.specialized.BlobLeaseClient;
import com.azure.storage.blob.specialized.BlobLeaseClientBuilder;
import com.azure.storage.blob.specialized.BlockBlobClient;
//import com.azure.storage.common.ParallelTransferOptions;
import com.azurestorageaccount.Model.ModifyingBlob;
import com.microsoft.azure.storage.AccessCondition;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.server.Encoding;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;;
import java.security.InvalidKeyException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.*;

import org.springframework.http.ResponseEntity;


import com.azure.storage.blob.sas.BlobServiceSasSignatureValues;
import com.azure.storage.blob.sas.BlobSasPermission;

@Service
@Slf4j
public class BlobServiceForStorageAccountOne {
    @Value("${azure.storage.account-connection-string}")
    private String connectionString;

    @Value("${azure.storage.container-create-name}")
    private String createContainerName;

    @Value("${azure.storage.upload-container}")
    private String uploadContainerName;

    @Value("${azure.storage.get}")
    private String getContainerName;

    private String blobClientName = "upload.txt";

    private String local_blob = "D:\\pm\\AZ-204\\Azure-storage account\\contaier file upload";

    public String createContainerInAzureStorageAccount() {
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                .connectionString(connectionString).buildClient();
        blobServiceClient.createBlobContainer(createContainerName);
        log.info("container created successfully");
        return "container created successfully";

    }

    public String uploadBlobToAzureStorageContainer() throws FileNotFoundException {
        BlobContainerClient blobContainerClient = getBlobContainerClient(uploadContainerName);
        blobClientName = Timestamp.from(Instant.now()) + "_" + blobClientName;
        BlobClient blobClient = blobContainerClient.getBlobClient(blobClientName);
        final String filePath = "D:\\pm\\AZ-204\\Azure-storage account\\upload.txt";
        File source = new File(filePath);
        try {
            blobClient.upload(new FileInputStream(source), source.length());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        log.info("blob uploaded successfully:");
        return "blob uploaded successfully";

    }


    public String uploadBlobToAzureStorageContainerDynamic(MultipartFile multipartFile) throws FileNotFoundException {
        BlobContainerClient blobContainerClient = getBlobContainerClient(uploadContainerName);
        blobClientName = Timestamp.from(Instant.now()) + "_" + multipartFile.getOriginalFilename();
        BlobClient blobClient = blobContainerClient.getBlobClient(blobClientName);
        try {
            blobClient.upload(multipartFile.getInputStream(), multipartFile.getSize());
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("blob uploaded successfully:");
        return "blob uploaded successfully";

    }


    public List<String> getAllBlobsInContainer() {
        List<String> blobNames = new ArrayList<>();
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                .connectionString(connectionString).buildClient();
        BlobContainerClient blobContainerClient = blobServiceClient.getBlobContainerClient(getContainerName);
        PagedIterable<BlobItem> blobItems = blobContainerClient.listBlobs();
        blobNames = blobItems.mapPage(blobItem -> blobItem.getName()).stream().toList();
        return blobNames;
    }


    public String downloadBlobToLocal() {
        String blobName = "madhuratext.txt";
        BlobClient blob_client = getBlobClient(getContainerName, blobName);
        blob_client.downloadToFile(local_blob);
        log.info("file downloaded successfully");
        return "file downloaded successfully";
    }

    public String readBlobFromBlobSas() {
        String sasUrl = generateBlobSAS();
        BlobClient blobClient = new BlobClientBuilder()
                .endpoint(sasUrl)
                .buildClient();
        String m = blobClient.downloadContent().toString();
        log.info("download content successfully:{}", m);
        return m;
    }

    public String generateBlobSAS() {
        BlobSasPermission blobSasPermission = new BlobSasPermission()
                .setReadPermission(true);
        OffsetDateTime expiryTime = OffsetDateTime.now().plusDays(7);
        BlobServiceSasSignatureValues values =
                new BlobServiceSasSignatureValues(expiryTime, blobSasPermission)
                        .setStartTime(OffsetDateTime.now());
        String blobName = "madhuratext.txt";
        BlobClient blob_client = getBlobClient(getContainerName, blobName);
        String sasUrl = String.join("", blob_client.getBlobUrl(), "?", blob_client.generateSas(values));

        log.info("sas url is:{}", sasUrl);
        return sasUrl;
    }


    public BlobProperties getBlobProperties() {
        String blobName = "madhuratext.txt";
        BlobClient blob_client = getBlobClient(getContainerName, blobName);
        return blob_client.getProperties();
    }



    public Map<String,String> getMetadataFromBlob()
    {
        String blobName = "madhuratext.txt";
        BlobClient blob_client = getBlobClient(getContainerName, blobName);
        BlobProperties blobProperties=blob_client.getProperties();
        return blobProperties.getMetadata();
    }


    public Map<String, String> setMetadataFromBlob(Map<String, String> metadata) {
        String blobName = "madhuratext.txt";
        BlobClient blob_client = getBlobClient(getContainerName, blobName);
        BlobProperties blobProperties=blob_client.getProperties();
        Map<String, String> setMetaData=blobProperties.getMetadata();
        setMetaData.putAll(metadata);
        blob_client.setMetadata(setMetaData);
        return blob_client.getProperties().getMetadata();
    }

    public ResponseEntity<ByteArrayResource> usingMemoryStreamReadWithoutDownloadToLocal()
    {
        String blobName = "madhuratext.txt";
        BlobClient blob_client = getBlobClient(getContainerName, blobName);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        blob_client.download(outputStream);

        //converting it to the inputStream to return
        final byte[] bytes = outputStream.toByteArray();
        ByteArrayResource resource = new ByteArrayResource(bytes);
        return ResponseEntity.ok()
                .body(resource);
    }

    public String usingMemoryStreamUploadWithoutLocalUpload(ModifyingBlob modifyingBlob)
    {
        String blobName = "madhuratext.txt";
        BlobClient blob_client = getBlobClient(getContainerName, blobName);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        blob_client.download(outputStream);

        //converting it to the inputStream to return
        final byte[] bytes1 = outputStream.toByteArray();
        InputStream inputStream = new ByteArrayInputStream(bytes1);
        String begging=modifyingBlob.beggingData+"\r\n";
        String ending="\r\n"+modifyingBlob.endingData;
        List<InputStream> streams = Arrays.asList(
                new ByteArrayInputStream(begging.getBytes()),
                inputStream,
                new ByteArrayInputStream(ending.getBytes()));
        InputStream modifyingData = new SequenceInputStream(Collections.enumeration(streams));
            blob_client.upload(BinaryData.fromStream(modifyingData),true);
            return "blob modified successfully";
    }


    public String storageLease(ModifyingBlob modifyingBlob) throws URISyntaxException, InvalidKeyException, StorageException {
   // here first get modifying data then modify existing file  madhuratext.txt
        // then we upload content to blob

        String blobName = "madhuratext.txt";
        BlobClient blob_client = getBlobClient(getContainerName, blobName);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        blob_client.download(outputStream);

        //converting it to the inputStream to return
        final byte[] bytes1 = outputStream.toByteArray();
        InputStream inputStream = new ByteArrayInputStream(bytes1);
        String begging=modifyingBlob.beggingData+"\r\n";
        String ending="\r\n"+modifyingBlob.endingData;
        List<InputStream> streams = Arrays.asList(
                new ByteArrayInputStream(begging.getBytes()),
                inputStream,
                new ByteArrayInputStream(ending.getBytes()));
        InputStream modifyingData = new SequenceInputStream(Collections.enumeration(streams));

      String filePath = "D:\\pm\\AZ-204\\Azure-storage account\\contaier file upload\\madhuratext.txt";

       File file = new File(filePath);

        try {
            FileUtils.copyInputStreamToFile(modifyingData, file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        BlobLeaseClient blobLeaseClient = new BlobLeaseClientBuilder()
                .blobClient(blob_client)
                .buildClient();
        blobLeaseClient.acquireLease(30);
        String leaseId = blobLeaseClient.getLeaseId();

        BlobHttpHeaders headers = new BlobHttpHeaders()
                .setContentLanguage("en-US")
                .setContentType("binary");

        Map<String, String> metadata = Collections.singletonMap("metadata", "value");
        BlobRequestConditions requestConditions = new BlobRequestConditions()
                .setLeaseId(leaseId);
        Long blockSize = 100L * 1024L * 1024L; // 100 MB;
        ParallelTransferOptions parallelTransferOptions = new ParallelTransferOptions().setBlockSizeLong(blockSize);

        try {
            Duration timeout = Duration.ofDays(4);
            blob_client.uploadFromFile(filePath, parallelTransferOptions, headers, metadata,
                    AccessTier.HOT, requestConditions, timeout);
            System.out.println("Upload from file succeeded");
        } catch (UncheckedIOException ex) {
            System.err.printf("Failed to upload from file %s%n", ex.getMessage());
        }

        blobLeaseClient.releaseLease();
               return "blob modified successfully";
    }



    public BlobClient getBlobClient(String containerName, String blobName) {
        BlobServiceClient blobServiceClient =
                new BlobServiceClientBuilder().connectionString(connectionString).buildClient();
        BlobContainerClient srcContClient =
                blobServiceClient.getBlobContainerClient(containerName);
        BlobClient blob_client = srcContClient.getBlobClient(blobName);
        return blob_client;
    }


    public BlobContainerClient getBlobContainerClient(String containerName)
    {
        BlobServiceClient blobServiceClient =
                new BlobServiceClientBuilder().connectionString(connectionString).buildClient();
        BlobContainerClient blobContainerClient =
                blobServiceClient.getBlobContainerClient(containerName);
        return blobContainerClient;
    }


}


