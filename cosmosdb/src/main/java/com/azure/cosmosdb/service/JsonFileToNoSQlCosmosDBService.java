package com.azure.cosmosdb.service;

import com.azure.cosmos.CosmosContainer;
import com.azure.cosmos.models.CosmosItemRequestOptions;
import com.azure.cosmos.models.PartitionKey;
import com.azure.cosmosdb.Model.Activity;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
@Slf4j
public class JsonFileToNoSQlCosmosDBService {

    @Autowired
    private CosmosContainer jsonFileContainer;

    public void jsonFileToNoSQlCosmosDB() throws IOException {
        final JsonFactory jsonFactory = new JsonFactory();
        final ObjectMapper jsonObjectMapper = new ObjectMapper(jsonFactory);
        JsonParser jsonParser = jsonFactory.createParser(new File("D:\\Azure-Springboot-developing-2022-ness\\cosmosdb\\QueryResult.json"));
        jsonFactory.setCodec(jsonObjectMapper);
        JsonToken current;
        current = jsonParser.nextToken();
        int i = 0;
        String id = "c";
        if (current != JsonToken.START_ARRAY) {
            log.info("json parser:{}", current);
            System.out.println("Error: root should be object: quiting.");
            return;
        }
        while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
            JsonNode node = jsonParser.readValueAsTree();
            Activity activity = jsonObjectMapper.treeToValue(node, Activity.class);
            activity.setId(id + i++);
            jsonFileContainer.createItem(activity, new PartitionKey(activity.getOperationname()), new CosmosItemRequestOptions());
            log.info("activity class:{}", new ObjectMapper().writeValueAsString(activity));
        }


    }


}
