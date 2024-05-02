package com.azure.cosmosdb.service;

import com.azure.cosmos.CosmosContainer;
import com.azure.cosmos.models.CosmosBulkOperations;
import com.azure.cosmos.models.CosmosItemOperation;
import com.azure.cosmos.models.PartitionKey;
import com.azure.cosmosdb.Model.Customer;
import com.azure.cosmosdb.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class NoSQlEmbeddingDataService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CosmosContainer embeddedContainer;

    public void EmbeddedDataInsert(List<Customer> courseList) throws Exception {
        Iterable<CosmosItemOperation> cosmosItemOperations = courseList.stream().
                map(
                        customer-> {
                            return CosmosBulkOperations.getCreateItemOperation(customer, new PartitionKey(customer.getLastName()));
                        })
                .collect(Collectors.toList());
        embeddedContainer.executeBulkOperations(cosmosItemOperations);
        log.info("mad: {}",cosmosItemOperations);
        // container.createItem(cosmosItemOperations);

    }
}
