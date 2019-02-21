package io.pivotal.demo.pcc.model.gf;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.geode.pdx.PdxInstance;
import org.springframework.data.annotation.Id;
import org.springframework.data.gemfire.mapping.annotation.Region;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Region("customer")
public class Customer {
    @Id
    private String id;
    private String name;

    public Customer(PdxInstance pdxInstance) {
        this.id = (String) pdxInstance.getField("id");
        this.name = (String) pdxInstance.getField("name");
    }
}
