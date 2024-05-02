package com.azurestorageaccount.Model;

import com.microsoft.azure.storage.table.TableServiceEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Customer extends TableServiceEntity {

    private String name;
    public Customer(String name,String city,String customerId)
    {
        this.name=name;
        this.partitionKey=city;
        this.rowKey=customerId;
    }
}

/*{
        "name":"emimanushulo",
        "partitionKey":"tirupati",
        "rowKey":"2"
     }

 */
