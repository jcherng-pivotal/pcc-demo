package io.pivotal.demo.pcc.repository.jpa;

import io.pivotal.demo.pcc.model.jpa.CustomerEntity;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional
@Rollback
public class CustomerRepositoryTest {

    @Autowired
    private CustomerEntityRepository customerEntityRepository;

    @Before
    public void setUp() {
        CustomerEntity customer1 = new CustomerEntity("customer1");
        customer1.setName("Krikor Garegin");
        customerEntityRepository.save(customer1);

        CustomerEntity customer2 = new CustomerEntity("customer2");
        customer2.setName("Ararat Avetis");
        customerEntityRepository.save(customer2);
    }

    @Test
    public void testCount() {
        Assert.assertEquals(2, customerEntityRepository.count());
    }

    @Test
    public void testFindByName() {
        Set<CustomerEntity> customerSet = customerEntityRepository.findByName("Krikor Garegin");
        Assert.assertEquals(1, customerSet.size());
    }

}
