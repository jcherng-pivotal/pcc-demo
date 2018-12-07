package io.pivotal.demo.pcc.repository.jpa;

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
import java.util.Set;

@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional
@Rollback
public class ItemEntityRepositoryTest {

    @Autowired
    private ItemEntityRepository itemEntityRepository;

    @Before
    public void setUp() {
        ItemEntity pencil = new ItemEntity("pencil");
        pencil.setName("pencil");
        pencil.setDescription("pencil decription");
        pencil.setPrice(new BigDecimal("0.99"));
        itemEntityRepository.save(pencil);

        ItemEntity pen = new ItemEntity("pen");
        pen.setName("pen");
        pen.setDescription("pen description");
        pen.setPrice(new BigDecimal("1.49"));
        itemEntityRepository.save(pen);

        ItemEntity paper = new ItemEntity("paper");
        paper.setName("pen");
        paper.setDescription("paper description");
        paper.setPrice(new BigDecimal("0.10"));
        itemEntityRepository.save(paper);
    }

    @Test
    public void testCount() {
        Assert.assertEquals(3, itemEntityRepository.count());
    }

    @Test
    public void testFindByName() {
        Set<ItemEntity> itemSet = itemEntityRepository.findByName("book");
        Assert.assertEquals(0, itemSet.size());

        itemSet = itemEntityRepository.findByName("pencil");
        Assert.assertEquals(1, itemSet.size());
    }

}
