package io.pivotal.demo.pcc.model.gf;

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
@Region("item")
public class Item {
    @Id
    private String id;
    private String name;
    private String description;
    private String price;

    public Item(PdxInstance pdxInstance) {
        this.id = (String) pdxInstance.getField("id");
        this.name = (String) pdxInstance.getField("name");
        this.description = (String) pdxInstance.getField("description");
        this.price = (String) pdxInstance.getField("price");
    }
}
