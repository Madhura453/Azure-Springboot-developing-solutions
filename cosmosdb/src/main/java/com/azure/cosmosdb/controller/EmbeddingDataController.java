package com.azure.cosmosdb.controller;

import com.azure.cosmosdb.Model.Customer;
import com.azure.cosmosdb.service.NoSQlEmbeddingDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EmbeddingDataController {

    @Autowired
    private NoSQlEmbeddingDataService embeddingDataService;

    @PostMapping("/embedded/customer")
    public void embeddedData(@RequestBody List<Customer> customerList) throws Exception {
        embeddingDataService.EmbeddedDataInsert(customerList);
    }
}

 /*
   [  {
        "id":"2",
            "customerId":"az",
            "customerName":"java",
         "orders":
       [ {
            "orderId":"12",
            "price":20000

        }
       ]
    }
  ]


  */
