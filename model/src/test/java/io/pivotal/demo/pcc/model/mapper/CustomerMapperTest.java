package io.pivotal.demo.pcc.model.mapper;

import io.pivotal.demo.pcc.model.gf.Customer;
import io.pivotal.demo.pcc.model.io.CustomerIO;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CustomerMapperTest {

    @Test
    public void getCustomer() {
        CustomerIO customerIO = CustomerIO
                .builder()
                .id("customer1")
                .name("Krikor Garegin")
                .build();

        Customer customer = CustomerMapper.MAPPER.map(customerIO);

        assertThat(customer.getId(), is(customerIO.getId()));
        assertThat(customer.getName(), is(customerIO.getName()));
    }

    @Test
    public void getCustomerIO() {
        Customer customer = Customer
                .builder()
                .id("customer1")
                .name("Krikor Garegin")
                .build();

        CustomerIO customerIO = CustomerMapper.MAPPER.map(customer);

        assertThat(customerIO.getId(), is(customer.getId()));
        assertThat(customerIO.getName(), is(customer.getName()));
    }
}