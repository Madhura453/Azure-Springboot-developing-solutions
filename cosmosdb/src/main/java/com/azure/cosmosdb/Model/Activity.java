package com.azure.cosmosdb.Model;

import com.azure.spring.data.cosmos.core.mapping.Container;
import com.azure.spring.data.cosmos.core.mapping.PartitionKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

@Container(containerName = "jsonfilecontainer")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Activity implements Serializable {

    public Activity(String correlationid, String operationname, String status, String eventcategory, String level) {
        Correlationid = correlationid;
        Operationname = operationname;
        Status = status;
        Eventcategory = eventcategory;
        Level = level;
    }

    @Id
    public String id;

    public String Correlationid;

    @PartitionKey
    public String Operationname;

    public String  Status;

    public String Eventcategory;

    public String Level;

}
