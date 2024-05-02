package com.azure.cosmosdb.repository;

import com.azure.cosmosdb.Model.Activity;
import com.azure.spring.data.cosmos.repository.ReactiveCosmosRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends ReactiveCosmosRepository<Activity,String> {
}
