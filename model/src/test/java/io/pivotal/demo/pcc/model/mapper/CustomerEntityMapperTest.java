package io.pivotal.demo.pcc.model.mapper;

import io.pivotal.demo.pcc.model.gf.Customer;
import io.pivotal.demo.pcc.model.jpa.CustomerEntity;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CustomerEntityMapperTest {

    @Test
    public void getCustomer() {
        CustomerEntity customerEntity = CustomerEntity
                .builder()
                .id("customer1")
                .name("Krikor Garegin")
                .build();

        Customer customer = CustomerEntityMapper.MAPPER.map(customerEntity);

        assertThat(customer.getId(), is(customerEntity.getId()));
        assertThat(customer.getName(), is(customerEntity.getName()));
    }
}