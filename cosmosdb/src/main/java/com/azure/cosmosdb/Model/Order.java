package com.azure.cosmosdb.Model;

import com.azure.spring.data.cosmos.core.mapping.Container;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Container(containerName = "embeddedData")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    public String orderId;

    public int price;

}
