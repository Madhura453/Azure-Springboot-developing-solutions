package com.azure.cosmosdb.Model;

import com.azure.spring.data.cosmos.core.mapping.Container;
import com.azure.spring.data.cosmos.core.mapping.PartitionKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;

@Container(containerName = "embeddedData")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    public String id;

    @PartitionKey
    public String lastName;

    public String phoneNumber;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="order_id",referencedColumnName="orderId")
    public List<Order> orders;

}
