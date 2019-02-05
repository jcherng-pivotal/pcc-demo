package io.pivotal.demo.pcc.model.mapper;

import io.pivotal.demo.pcc.model.gf.CustomerOrder;
import io.pivotal.demo.pcc.model.io.CustomerOrderIO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {ItemMapper.class, DateMapper.class})
public interface CustomerOrderMapper {

    CustomerOrderMapper MAPPER = Mappers.getMapper(CustomerOrderMapper.class);

    @Mapping(target = "collocatedId", expression = "java(source.getCustomerId() + \"|\" " +
            "+ source.getId())")
    CustomerOrder getCustomerOrder(CustomerOrderIO source);

    @Mapping(target = "items", ignore = true)
    CustomerOrderIO getCustomerOrderIO(CustomerOrder source);
}
