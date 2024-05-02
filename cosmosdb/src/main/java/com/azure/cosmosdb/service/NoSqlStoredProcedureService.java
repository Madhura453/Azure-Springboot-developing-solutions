package com.azure.cosmosdb.service;

import com.azure.cosmos.CosmosContainer;
import com.azure.cosmos.implementation.Utils;
import com.azure.cosmos.models.CosmosStoredProcedureRequestOptions;
import com.azure.cosmos.models.CosmosStoredProcedureResponse;
import com.azure.cosmos.models.PartitionKey;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class NoSqlStoredProcedureService {

    @Autowired
    private CosmosContainer container;

    private static final ObjectMapper OBJECT_MAPPER = Utils.getSimpleObjectMapper();

    public CosmosStoredProcedureResponse executeStoredProcedure(String stopName)
    {
        CosmosStoredProcedureRequestOptions cosmosStoredProcedureRequestOptions
        =new CosmosStoredProcedureRequestOptions();
        cosmosStoredProcedureRequestOptions.setPartitionKey(new PartitionKey("madhu"));
        CosmosStoredProcedureResponse cosmosStoredProcedureResponse=
                container.getScripts().getStoredProcedure(stopName)
                        .execute(null,cosmosStoredProcedureRequestOptions);
        return cosmosStoredProcedureResponse;
    }

    public CosmosStoredProcedureResponse executeStoredProcedureWithparams(String stopname, List<Object> courseList) throws JsonProcessingException {
// stopname = StoredProcedureName
     // calling stored procedure with parameters
        String partitionValue = "az";
        CosmosStoredProcedureRequestOptions options = new CosmosStoredProcedureRequestOptions();
        options.setPartitionKey(new PartitionKey(partitionValue));
        List<Object> sproc_args = new ArrayList<>();
        sproc_args.add(OBJECT_MAPPER.writeValueAsString(courseList));
        log.info("sproc args:{}",sproc_args);
        CosmosStoredProcedureResponse executeResponse = container.getScripts()
                .getStoredProcedure(stopname)
                .execute(courseList, options);

       log.info(String.format("Stored procedure %s returned %s (HTTP %d), at cost %.3f RU.\n",
                stopname+"ArrayArg",
                executeResponse.getResponseAsString(),
                executeResponse.getStatusCode(),
                executeResponse.getRequestCharge()));
       return executeResponse;
    }
}
