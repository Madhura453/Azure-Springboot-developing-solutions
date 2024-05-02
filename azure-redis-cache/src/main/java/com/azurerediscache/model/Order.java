package com.azurerediscache.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.io.Serializable;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class Order implements Serializable {
    private static final long serialVersionUID = 1L;
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