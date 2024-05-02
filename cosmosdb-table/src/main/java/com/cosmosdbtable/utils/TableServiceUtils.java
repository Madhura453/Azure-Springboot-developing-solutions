package com.cosmosdbtable.utils;

import com.azure.data.tables.models.TableEntity;
import com.cosmosdbtable.Model.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class TableServiceUtils {

    public TableEntity createTableEntity(Employee employee)
    {
      TableEntity tableEntity=new TableEntity(employee.department,employee.getEid());
        Map<String,Object> properties=tableEntity.getProperties();
        properties.put("name",employee.getName());
        properties.put("salary",employee.getSalary());
        return tableEntity;
    }

}
