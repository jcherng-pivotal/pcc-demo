package io.pivotal.demo.pcc.repository.gf;

import io.pivotal.demo.pcc.model.gf.Customer;
import io.pivotal.demo.pcc.model.gf.CustomerOrder;
import io.pivotal.demo.pcc.model.gf.Item;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@DirtiesContext
public class CustomerOrderRepositoryTest {
    @Autowired
    CustomerOrderRepository customerOrderRepository;

    @Before
    public void setUp() {
        Customer customer1 = Customer
                .builder()
                .id("customer1")
                .name("Krikor Garegin")
                .build();

        Customer customer2 = Customer
                .builder()
                .id("customer2")
                .name("Ararat Avetis")
                .build();

        Item pencil = Item
                .builder()
                .id("pencil")
                .name("pencil")
                .description("pencil description")
                .price("0.99")
                .build();

        Item pen = Item
                .builder()
                .id("pen")
                .name("pen")
                .description("pen description")
                .price("1.49")
                .build();

        Item paper = Item
                .builder()
                .id("paper")
                .name("paper")
                .description("paper description")
                .price("0.10")
                .build();

        Map<String, CustomerOrder> dataMap = new HashMap<>();
        Set<String> itemSet = new HashSet<>();
        itemSet.add(pen.getId());
        itemSet.add(paper.getId());
        // 1.49 + 0.10 = 1.59
        CustomerOrder customerOrder = CustomerOrder
                .builder()
                .collocatedId(customer1.getId() + "|" + "order1")
                .id("order1")
                .customerId(customer1.getId())
                .shippingAddress("address1")
                .orderDate(new Date().getTime())
                .items(itemSet)
                .build();
        dataMap.put(customerOrder.getCollocatedId(), customerOrder);

        itemSet = new HashSet<>();
        itemSet.add(pencil.getId());
        itemSet.add(pen.getId());
        itemSet.add(paper.getId());
        // 1.59 + 0.99 = 2.58
        customerOrder = CustomerOrder
                .builder()
                .collocatedId(customer1.getId() + "|" + "order2")
                .id("order1")
                .customerId(customer1.getId())
                .shippingAddress("address1")
                .orderDate(new Date().getTime())
                .items(itemSet)
                .build();
        dataMap.put(customerOrder.getCollocatedId(), customerOrder);

        itemSet = new HashSet<>();
        itemSet.add(pencil.getId());
        itemSet.add(pen.getId());
        // 0.99 + 1.49 = 2.48
        customerOrder = CustomerOrder
                .builder()
                .collocatedId(customer2.getId() + "|" + "order3")
                .id("order1")
                .customerId(customer2.getId())
                .shippingAddress("address2")
                .orderDate(new Date().getTime())
                .items(itemSet)
                .build();
        dataMap.put(customerOrder.getCollocatedId(), customerOrder);
        customerOrderRepository.saveAll(dataMap.values());
    }

    @After
    public void tearDown() {
    }

    @Test
    public void findByCustomerId() {
        List<CustomerOrder> customerOrders = customerOrderRepository.findByCustomerId("customer1");
        assertThat(customerOrders.size(), is(2));

        customerOrders = customerOrderRepository.findByCustomerId("customer2");
        assertThat(customerOrders.size(), is(1));

        customerOrders = customerOrderRepository.findByCustomerId("customer3");
        assertThat(customerOrders.size(), is(0));
    }
}