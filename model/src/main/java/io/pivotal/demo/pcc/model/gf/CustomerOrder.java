package io.pivotal.demo.pcc.model.gf;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.gemfire.mapping.annotation.Region;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Region("customer-order")
public class CustomerOrder {
    @Id
    private String collocatedId;
    private String id;
    private String customerId;
    private String shippingAddress;
    private long orderDate;
    private Set<String> items;
}
