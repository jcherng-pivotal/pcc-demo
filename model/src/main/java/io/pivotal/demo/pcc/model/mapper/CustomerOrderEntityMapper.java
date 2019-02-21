package io.pivotal.demo.pcc.model.mapper;

import io.pivotal.demo.pcc.model.gf.CustomerOrder;
import io.pivotal.demo.pcc.model.jpa.CustomerOrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {ItemEntityMapper.class, DateMapper.class})
public interface CustomerOrderEntityMapper {

    CustomerOrderEntityMapper MAPPER = Mappers.getMapper(CustomerOrderEntityMapper.class);

    @Mappings({
            @Mapping(target = "items", source = "source.itemSet"),
            @Mapping(target = "customerId", expression = "java(source.getCustomer().getId())"),
            @Mapping(target = "collocatedId", expression = "java(source.getCustomer().getId() + \"|\" " +
                    "+ source.getId())")
    })
    CustomerOrder map(CustomerOrderEntity source);
}
