package com.azurestorageaccount.config;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.table.CloudTable;
import com.microsoft.azure.storage.table.CloudTableClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URISyntaxException;
import java.security.InvalidKeyException;

@Configuration
public class TableClientProvider {

    @Value("${azure.storage.account-connection-string}")
    private String connectionString;

    @Value("${azure.storage.table}")
    private String tableName;

    @Bean
    public CloudTableClient createTableClient() {
        CloudStorageAccount cloudStorageAccount = null;
        try {
            cloudStorageAccount = CloudStorageAccount.parse(connectionString);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return cloudStorageAccount.createCloudTableClient();
    }



}
