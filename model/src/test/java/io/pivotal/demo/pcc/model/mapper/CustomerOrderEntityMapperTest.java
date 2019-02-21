package io.pivotal.demo.pcc.model.mapper;

import io.pivotal.demo.pcc.model.gf.CustomerOrder;
import io.pivotal.demo.pcc.model.jpa.CustomerEntity;
import io.pivotal.demo.pcc.model.jpa.CustomerOrderEntity;
import io.pivotal.demo.pcc.model.jpa.ItemEntity;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CustomerOrderEntityMapperTest {

    @Test
    public void getCustomerOrder() {
        Date orderDate = new Date();
        Set<ItemEntity> items = new HashSet<>();

        ItemEntity itemEntity = ItemEntity
                .builder()
                .id("pen")
                .name("pen")
                .description("pen description")
                .price(new BigDecimal("1.49"))
                .build();
        items.add(itemEntity);

        itemEntity = ItemEntity
                .builder()
                .id("paper")
                .name("paper")
                .description("paper description")
                .price(new BigDecimal("0.10"))
                .build();
        items.add(itemEntity);

        CustomerEntity customerEntity = CustomerEntity
                .builder()
                .id("customer1")
                .build();

        CustomerOrderEntity customerOrderEntity = CustomerOrderEntity
                .builder()
                .id("order1")
                .customer(customerEntity)
                .shippingAddress("address1")
                .orderDate(orderDate)
                .itemSet(items)
                .build();

        CustomerOrder customerOrder = CustomerOrderEntityMapper.MAPPER.map(customerOrderEntity);

        assertThat(customerOrder.getCollocatedId(), is(customerOrderEntity.getCustomer().getId() + "|" + customerOrderEntity.getId()));
        assertThat(customerOrder.getId(), is(customerOrderEntity.getId()));
        assertThat(customerOrder.getCustomerId(), is(customerOrderEntity.getCustomer().getId()));
        assertThat(customerOrder.getShippingAddress(), is(customerOrderEntity.getShippingAddress()));
        assertThat(customerOrder.getOrderDate(), is(customerOrderEntity.getOrderDate().getTime()));
        items.stream().forEach(item -> assertThat(customerOrder.getItems().contains(item.getId()), is(true)));
    }
}