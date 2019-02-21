package io.pivotal.demo.pcc.model.mapper;

import io.pivotal.demo.pcc.model.gf.Item;
import io.pivotal.demo.pcc.model.jpa.ItemEntity;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ItemEntityMapperTest {

    @Test
    public void getItem() {
        ItemEntity itemEntity = ItemEntity
                .builder()
                .id("pen")
                .name("pen")
                .description("pen description")
                .price(new BigDecimal("1.49"))
                .build();

        Item item = ItemEntityMapper.MAPPER.map(itemEntity);

        assertThat(item.getId(), is(itemEntity.getId()));
        assertThat(item.getName(), is(itemEntity.getName()));
        assertThat(item.getDescription(), is(itemEntity.getDescription()));
        assertThat(item.getPrice(), is(itemEntity.getPrice().toString()));
    }

    @Test
    public void map() {
        List<ItemEntity> itemEntityList = new ArrayList<>();

        ItemEntity itemEntity = ItemEntity
                .builder()
                .id("pen")
                .name("pen")
                .description("pen description")
                .price(new BigDecimal("1.49"))
                .build();
        itemEntityList.add(itemEntity);

        itemEntity = ItemEntity
                .builder()
                .id("paper")
                .name("paper")
                .description("paper description")
                .price(new BigDecimal("0.10"))
                .build();
        itemEntityList.add(itemEntity);

        Set<String> itemIdSet = ItemEntityMapper.MAPPER.mapIds(itemEntityList);
        itemEntityList.stream().forEach(item -> assertThat(itemIdSet.contains(item.getId()), is(true)));
    }

}