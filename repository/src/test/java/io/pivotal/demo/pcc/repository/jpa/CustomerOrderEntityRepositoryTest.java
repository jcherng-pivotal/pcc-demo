package io.pivotal.demo.pcc.repository.jpa;

import io.pivotal.demo.pcc.model.jpa.CustomerEntity;
import io.pivotal.demo.pcc.model.jpa.CustomerOrderEntity;
import io.pivotal.demo.pcc.model.jpa.ItemEntity;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional
@Rollback
public class CustomerOrderEntityRepositoryTest {

    @Autowired
    private CustomerEntityRepository customerEntityRepository;

    @Autowired
    private ItemEntityRepository itemEntityRepository;

    @Autowired
    private CustomerOrderEntityRepository customerOrderEntityRepository;

    @Before
    public void setUp() {
        CustomerEntity customer1 = CustomerEntity.builder().id("customer1").build();
        customer1.setName("Krikor Garegin");
        customerEntityRepository.save(customer1);

        CustomerEntity customer2 = CustomerEntity.builder().id("customer2").build();
        customer2.setName("Ararat Avetis");
        customerEntityRepository.save(customer2);

        ItemEntity pencil = ItemEntity.builder().id("pencil").build();
        pencil.setName("pencil");
        pencil.setDescription("pencil description");
        pencil.setPrice(new BigDecimal("0.99"));
        itemEntityRepository.save(pencil);

        ItemEntity pen = ItemEntity.builder().id("pen").build();
        pen.setName("pen");
        pen.setDescription("pen description");
        pen.setPrice(new BigDecimal("1.49"));
        itemEntityRepository.save(pen);

        ItemEntity paper = ItemEntity.builder().id("paper").build();
        paper.setName("pen");
        paper.setDescription("paper description");
        paper.setPrice(new BigDecimal("0.10"));
        itemEntityRepository.save(paper);

        Set<ItemEntity> itemSet = new HashSet<ItemEntity>();
        itemSet.add(pen);
        itemSet.add(paper);
        CustomerOrderEntity customerOrder = new CustomerOrderEntity("order1", customer1, "address1", new Date(), itemSet);
        customerOrderEntityRepository.save(customerOrder);

        itemSet = new HashSet<ItemEntity>();
        itemSet.add(pencil);
        itemSet.add(pen);
        itemSet.add(paper);
        customerOrder = new CustomerOrderEntity("order2", customer1, "address1", new Date(), itemSet);
        customerOrderEntityRepository.save(customerOrder);

        itemSet = new HashSet<ItemEntity>();
        itemSet.add(pencil);
        itemSet.add(pen);
        customerOrder = new CustomerOrderEntity("order3", customer2, "address2", new Date(), itemSet);
        customerOrderEntityRepository.save(customerOrder);
    }

    @Test
    public void testCount() {
        Assert.assertEquals(3, customerOrderEntityRepository.count());
    }

    @Test
    public void testFindByCustomer() {
        CustomerEntity customer1 = CustomerEntity.builder().id("customer1").build();
        customer1.setName("Krikor Garegin");
        Set<CustomerOrderEntity> customerOrderSet = customerOrderEntityRepository.findByCustomer(customer1);
        Assert.assertEquals(2, customerOrderSet.size());
        for (CustomerOrderEntity order : customerOrderSet) {
            Assert.assertEquals(customer1, order.getCustomer());
            Assert.assertNotEquals(0, order.getItemSet().size());
        }

        CustomerEntity customer2 = CustomerEntity.builder().id("customer2").build();
        customer2.setName("Ararat Avetis");
        customerOrderSet = customerOrderEntityRepository.findByCustomer(customer2);
        Assert.assertEquals(1, customerOrderSet.size());
        for (CustomerOrderEntity order : customerOrderSet) {
            Assert.assertEquals(customer2, order.getCustomer());
            Assert.assertNotEquals(0, order.getItemSet().size());
        }

    }

}
