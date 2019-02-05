package io.pivotal.demo.pcc.model.gf;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.gemfire.mapping.annotation.Region;

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
}
