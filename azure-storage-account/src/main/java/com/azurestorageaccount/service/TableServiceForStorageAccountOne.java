package com.azurestorageaccount.service;

import com.azurestorageaccount.Model.Customer;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.table.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


@Service
public class TableServiceForStorageAccountOne {

    @Autowired
    private CloudTableClient createTableClient;

    @Value("${azure.storage.table}")
    private String tableName;

    public CloudTable createCustomerTable() {
        CloudTable table = null;
        try {
            table = createTableClient.getTableReference(tableName);
            table.createIfNotExists();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (StorageException e) {
            e.printStackTrace();
        }
        return table;
    }

    public TableResult insertData(Customer customer) {
        CloudTable cloudTable = createCustomerTable();
        try {
            return cloudTable.execute(TableOperation.insert(customer));
        } catch (StorageException e) {
            e.printStackTrace();
            return null;
        }
    }

    // partition key is same for batch execution

    public ArrayList<TableResult> batchInsertOfCustomerEntities(List<Customer> customerList) {
        CloudTable cloudTable = createCustomerTable();
        TableBatchOperation tableBatchOperations = new TableBatchOperation();
//        List<TableOperation> customerOperations= customerList.stream().map(customer -> new TableOperation(customer,TableOperationType.INSERT))
//                ;
//        tableBatchOperations.addAll(customerOperations);
        customerList.stream().forEach(customer -> tableBatchOperations.insert(customer));
        try {
            return cloudTable.execute(tableBatchOperations);
        } catch (StorageException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Customer getCustomer(String partitionKey, String rowKey) {
        CloudTable cloudTable = createCustomerTable();
        try {
            return cloudTable.execute(TableOperation.retrieve(partitionKey, rowKey, Customer.class)).getResultAsType();
        } catch (StorageException e) {
            e.printStackTrace();
            return null;
        }
    }

    public TableResult updateCustomer(Customer customer) {
        CloudTable cloudTable = createCustomerTable();
        try {
            return cloudTable.execute(TableOperation.insertOrMerge(customer));
        } catch (StorageException e) {
            e.printStackTrace();
            return null;
        }
    }

    public TableResult deleteCustomer(String partitionKey, String rowKey) {
        CloudTable cloudTable = createCustomerTable();
        Customer customer = getCustomer(partitionKey, rowKey);
        try {
        return cloudTable.execute(TableOperation.delete(customer));
        } catch (StorageException e) {
            e.printStackTrace();
            return null;
        }

    }
}
