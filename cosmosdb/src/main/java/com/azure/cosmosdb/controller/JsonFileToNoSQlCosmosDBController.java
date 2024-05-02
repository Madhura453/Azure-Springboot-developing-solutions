package com.azure.cosmosdb.controller;

import com.azure.cosmosdb.service.JsonFileToNoSQlCosmosDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class JsonFileToNoSQlCosmosDBController {

    @Autowired
    private JsonFileToNoSQlCosmosDBService jsonFileToNoSQlCosmosDBService;

    @GetMapping("/jsonfile")
    public void jsonFileToNoSQlCosmosDB() throws IOException {
        jsonFileToNoSQlCosmosDBService.jsonFileToNoSQlCosmosDB();
    }
}
