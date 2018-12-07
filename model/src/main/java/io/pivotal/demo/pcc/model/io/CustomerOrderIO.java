package io.pivotal.demo.pcc.model.io;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerOrderIO implements Serializable {
    private String id;
    private String customerId;
    private String shippingAddress;
    private Date orderDate;
    private Iterable<ItemIO> items;
}
