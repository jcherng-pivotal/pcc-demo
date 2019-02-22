package io.pivotal.demo.pcc.model.gf;

import io.pivotal.demo.pcc.model.constant.RegionName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.geode.pdx.PdxInstance;
import org.springframework.data.annotation.Id;
import org.springframework.data.gemfire.mapping.annotation.Region;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Region(RegionName.CUSTOMER_ORDER)
public class CustomerOrder {
    @Id
    private String collocatedId;
    private String id;
    private String customerId;
    private String shippingAddress;
    private long orderDate;
    private Set<String> items;

    public CustomerOrder(PdxInstance pdxInstance) {
        this.collocatedId = (String) pdxInstance.getField("collocatedId");
        this.id = (String) pdxInstance.getField("id");
        this.customerId = (String) pdxInstance.getField("customerId");
        this.shippingAddress = (String) pdxInstance.getField("shippingAddress");
        this.orderDate = (Long) pdxInstance.getField("orderDate");
        this.items = (Set<String>) pdxInstance.getField("items");
    }
}
