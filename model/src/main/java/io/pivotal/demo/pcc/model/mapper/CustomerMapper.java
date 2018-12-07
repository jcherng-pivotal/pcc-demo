package io.pivotal.demo.pcc.model.mapper;

import io.pivotal.demo.pcc.model.gf.pdx.Customer;
import io.pivotal.demo.pcc.model.io.CustomerIO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {

    CustomerMapper MAPPER = Mappers.getMapper(CustomerMapper.class);

    Customer getCustomer(CustomerIO customerIO);

    CustomerIO getCustomerIO(Customer customer);
}
