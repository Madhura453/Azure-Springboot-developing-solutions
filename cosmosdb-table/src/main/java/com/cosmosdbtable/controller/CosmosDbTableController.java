package com.cosmosdbtable.controller;

import com.cosmosdbtable.Model.Employee;
import com.cosmosdbtable.service.CosmosDbTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CosmosDbTableController {

    @Autowired
    private CosmosDbTableService cosmosDbTableService;

    @PostMapping("/insert-employee")
    public String insertEntity(@RequestBody Employee employee)
    {
        return cosmosDbTableService.insertEntity(employee);
    }
}
