package com.azure.cosmosdb.repository;

import com.azure.cosmosdb.Model.Course;
import com.azure.cosmosdb.Model.Customer;
import com.azure.spring.data.cosmos.repository.Query;
import com.azure.spring.data.cosmos.repository.ReactiveCosmosRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CustomerRepository extends ReactiveCosmosRepository<Customer,String> {
}
