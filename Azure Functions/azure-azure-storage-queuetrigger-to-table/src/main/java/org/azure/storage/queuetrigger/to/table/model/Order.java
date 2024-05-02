package org.azure.storage.queuetrigger.to.table.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Order {
    private String PartitionKey;
    private String RowKey;
    public int quantity;
    public double unitPrice;
}

/*
{
"PartitionKey":"home",
"RowKey":"01",
"quantity":9,
 "unitPrice":8
 }
        */