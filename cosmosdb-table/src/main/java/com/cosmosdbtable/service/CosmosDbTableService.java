package com.cosmosdbtable.service;

import com.azure.data.tables.TableClient;
import com.cosmosdbtable.Model.Employee;
import com.cosmosdbtable.utils.TableServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CosmosDbTableService {

    @Autowired
    private TableClient tableClient;

    @Autowired
    private TableServiceUtils tableServiceUtils;

    public String insertEntity(Employee employee)
    {
        tableClient.createEntity(tableServiceUtils.createTableEntity(employee));
        return "insertedSuccessfully";
    }
}
