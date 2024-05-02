package com.azurestorageaccount.controller;

import com.azure.storage.blob.models.BlobProperties;
import com.azurestorageaccount.Model.ModifyingBlob;
import com.azurestorageaccount.service.BlobServiceForStorageAccountOne;
import com.microsoft.azure.storage.StorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.List;
import java.util.Map;

@RestController
public class BlobServiceAzureStorageController {

    @Autowired
    private BlobServiceForStorageAccountOne blobServiceForStorageAccountOne;

    @GetMapping("/upload-file-to-blob-storage")
    public String uploadBlobToAzureStorageContainerDynamic(@RequestParam("file") MultipartFile multipartFile) {
        try {
            return blobServiceForStorageAccountOne.uploadBlobToAzureStorageContainerDynamic(multipartFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "file not uploaded successfully";
        }
    }

    @GetMapping("/get-blobs-from-container")
    public List<String> getAllBlobsInContainer() {
        return blobServiceForStorageAccountOne.getAllBlobsInContainer();
    }

    @GetMapping("/download-blob-local")
    public String downloadBlobToLocal() {

        return blobServiceForStorageAccountOne.downloadBlobToLocal();
    }

    @GetMapping("/read-blob-from-blob-sas")
    public String readBlobFromBlobSas() {
        return blobServiceForStorageAccountOne.readBlobFromBlobSas();
    }

    @GetMapping("/get-blob-properties")
    public BlobProperties getBlobProperties() {
        return blobServiceForStorageAccountOne.getBlobProperties();
    }

    @GetMapping("/get-metadata-from-blob")
    public Map<String,String> getMetadataFromBlob() {
    return blobServiceForStorageAccountOne.getMetadataFromBlob();
    }

    @PostMapping("/set-metadata-to-blob")
    public Map<String,String> setMetadataFromBlob(@RequestBody Map<String,String> metadata) {
        return blobServiceForStorageAccountOne.setMetadataFromBlob(metadata);
    }

    @GetMapping("/using-memory-stream-without-local-read")
    public ResponseEntity<ByteArrayResource> usingMemoryStreamReadWithoutDownloadToLocal() {
        return blobServiceForStorageAccountOne.usingMemoryStreamReadWithoutDownloadToLocal();
    }


    @PostMapping("/using-memory-stream-without-local-upload")
    public String usingMemoryStreamUploadWithoutLocalUpload(@RequestBody ModifyingBlob modifyingBlob)
    {
       return blobServiceForStorageAccountOne.usingMemoryStreamUploadWithoutLocalUpload(modifyingBlob);
    }

    @PostMapping("/storage-lease")
    public String storageLease(@RequestBody ModifyingBlob modifyingBlob) throws URISyntaxException, InvalidKeyException, StorageException {
        return blobServiceForStorageAccountOne.storageLease(modifyingBlob);
    }
}
