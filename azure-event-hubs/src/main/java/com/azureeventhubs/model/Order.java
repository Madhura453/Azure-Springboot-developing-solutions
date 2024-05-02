package com.azureeventhubs.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Order {
    private String orderId;
    public int quantity;
    public double unitPrice;
    public String discountCategory;
}

/*
{
"orderId":"1",
"quantity":9,
 "unitPrice":8,
 "discountCategory":"childrens"
 }
        */