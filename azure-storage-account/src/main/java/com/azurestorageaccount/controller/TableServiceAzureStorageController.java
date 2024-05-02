package com.azurestorageaccount.controller;

import com.azurestorageaccount.Model.Customer;
import com.azurestorageaccount.service.TableServiceForStorageAccountOne;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.storage.table.TableResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class TableServiceAzureStorageController {
    @Autowired
    private TableServiceForStorageAccountOne tableServiceForStorageAccountOne;

    @PostMapping("/insert-customer")
    public TableResult insertCustomer(@RequestBody Customer customer)
    {
        try {
            log.info("rpa got updated: {}", new ObjectMapper().writeValueAsString(customer));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
       return tableServiceForStorageAccountOne.insertData(customer);
    }

    @PostMapping("/batch-customer")
    public ArrayList<TableResult> batchInsertOfCustomerEntities(@RequestBody List<Customer> customerList) {
    return tableServiceForStorageAccountOne.batchInsertOfCustomerEntities(customerList);
    }

    @GetMapping("/read-customer")
    public Customer getCustomer(@RequestParam("partitionKey") String partitionKey,@RequestParam("rowKey") String rowKey)
    {
        return tableServiceForStorageAccountOne.getCustomer(partitionKey,rowKey);
    }


    @PutMapping("/update-customer")
    public TableResult updateCustomer(@RequestBody Customer customer) {
        return tableServiceForStorageAccountOne.updateCustomer(customer);
    }

    @DeleteMapping("/delete-customer")
    public TableResult deleteCustomer(@RequestParam("partitionKey") String partitionKey,@RequestParam("rowKey") String rowKey)
    {
        return tableServiceForStorageAccountOne.deleteCustomer(partitionKey,rowKey);
    }
}
