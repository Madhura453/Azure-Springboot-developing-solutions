package com.azure.asb.topic.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Order {
    private Long orderId;
    private String name;
    public int quantity;
    public double unitPrice;
}

/*
{
"orderId":1,
"name":"home",
"quantity":9,
 "unitPrice":8
 }
        */