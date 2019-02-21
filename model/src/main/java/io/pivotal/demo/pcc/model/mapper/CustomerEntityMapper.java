package io.pivotal.demo.pcc.model.mapper;

import io.pivotal.demo.pcc.model.gf.Customer;
import io.pivotal.demo.pcc.model.jpa.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerEntityMapper {

    CustomerEntityMapper MAPPER = Mappers.getMapper(CustomerEntityMapper.class);

    Customer map(CustomerEntity source);
}
