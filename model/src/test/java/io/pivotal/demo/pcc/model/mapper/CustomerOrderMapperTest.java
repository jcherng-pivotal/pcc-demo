package io.pivotal.demo.pcc.model.mapper;

import io.pivotal.demo.pcc.model.gf.CustomerOrder;
import io.pivotal.demo.pcc.model.io.CustomerOrderIO;
import io.pivotal.demo.pcc.model.io.ItemIO;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

public class CustomerOrderMapperTest {

    @Test
    public void getCustomerOrder() {
        Date orderDate = new Date();
        List<ItemIO> items = new ArrayList<>();

        ItemIO itemIO = ItemIO
                .builder()
                .id("pen")
                .name("pen")
                .description("pen description")
                .price(new BigDecimal("1.49"))
                .build();
        items.add(itemIO);

        itemIO = ItemIO
                .builder()
                .id("paper")
                .name("paper")
                .description("paper description")
                .price(new BigDecimal("0.10"))
                .build();
        items.add(itemIO);

        CustomerOrderIO customerOrderIO = CustomerOrderIO
                .builder()
                .id("order1")
                .customerId("customer1")
                .shippingAddress("address1")
                .orderDate(orderDate)
                .items(items)
                .build();

        CustomerOrder customerOrder = CustomerOrderMapper.MAPPER.map(customerOrderIO);

        assertThat(customerOrder.getCollocatedId(), is(customerOrderIO.getCustomerId() + "|" + customerOrderIO.getId()));
        assertThat(customerOrder.getId(), is(customerOrderIO.getId()));
        assertThat(customerOrder.getCustomerId(), is(customerOrderIO.getCustomerId()));
        assertThat(customerOrder.getShippingAddress(), is(customerOrderIO.getShippingAddress()));
        assertThat(customerOrder.getOrderDate(), is(customerOrderIO.getOrderDate().getTime()));
        items.stream().forEach(item -> assertThat(customerOrder.getItems().contains(item.getId()), is(true)));
    }

    @Test
    public void getCustomerOrderIO() {
        Date orderDate = new Date();
        Set<String> items = new HashSet<>();
        items.add("paper");

        CustomerOrder customerOrder = CustomerOrder
                .builder()
                .collocatedId("customer1|order1")
                .id("order1")
                .customerId("customer1")
                .shippingAddress("address1")
                .orderDate(orderDate.getTime())
                .items(items)
                .build();

        CustomerOrderIO customerOrderIO = CustomerOrderMapper.MAPPER.map(customerOrder);

        assertThat(customerOrderIO.getId(), is(customerOrder.getId()));
        assertThat(customerOrderIO.getCustomerId(), is(customerOrder.getCustomerId()));
        assertThat(customerOrderIO.getShippingAddress(), is(customerOrder.getShippingAddress()));
        assertThat(customerOrderIO.getOrderDate(), is(new Date(customerOrder.getOrderDate())));
        assertThat(customerOrderIO.getItems(), nullValue());
    }
}