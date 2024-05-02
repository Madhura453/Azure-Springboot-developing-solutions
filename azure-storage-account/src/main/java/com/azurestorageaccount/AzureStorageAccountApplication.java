package com.azurestorageaccount;

import com.azurestorageaccount.service.BlobServiceForStorageAccountOne;
import com.azurestorageaccount.service.TableServiceForStorageAccountOne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AzureStorageAccountApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(AzureStorageAccountApplication.class, args);
    }

    @Autowired
    private BlobServiceForStorageAccountOne blobServiceForStorageAccountOne;

    @Autowired
    private TableServiceForStorageAccountOne tableServiceForStorageAccountOne;

    @Override
    public void run(String... args) throws Exception {
      //  blobServiceForStorageAccountOne.createContainerInAzureStorageAccount();
     //   blobServiceForStorageAccountOne.uploadBlobToAzureStorageContainer();
      //  tableServiceForStorageAccountOne.createCustomerTable();
    }
}
