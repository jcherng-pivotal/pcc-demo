package io.pivotal.demo.pcc.model.io;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemIO implements Serializable {
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
}
