package io.pivotal.demo.pcc.model.mapper;

import io.pivotal.demo.pcc.model.gf.pdx.Item;
import io.pivotal.demo.pcc.model.io.ItemIO;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ItemMapperTest {

    @Test
    public void getItem() {
        ItemIO itemIO = ItemIO
                .builder()
                .id("pen")
                .name("pen")
                .description("pen description")
                .price(new BigDecimal("1.49"))
                .build();

        Item item = ItemMapper.MAPPER.getItem(itemIO);

        assertThat(item.getId(), is(itemIO.getId()));
        assertThat(item.getName(), is(itemIO.getName()));
        assertThat(item.getDescription(), is(itemIO.getDescription()));
        assertThat(item.getPrice(), is(itemIO.getPrice().toString()));
    }

    @Test
    public void getItemIO() {
        Item item = Item
                .builder()
                .id("pen")
                .name("pen")
                .description("pen description")
                .price("1.49")
                .build();

        ItemIO itemIO = ItemMapper.MAPPER.getItemIO(item);

        assertThat(itemIO.getId(), is(item.getId()));
        assertThat(itemIO.getName(), is(item.getName()));
        assertThat(itemIO.getDescription(), is(item.getDescription()));
        assertThat(itemIO.getPrice(), is(new BigDecimal(item.getPrice())));
    }

    @Test
    public void map() {
        List<ItemIO> itemIOList = new ArrayList<>();

        ItemIO itemIO = ItemIO
                .builder()
                .id("pen")
                .name("pen")
                .description("pen description")
                .price(new BigDecimal("1.49"))
                .build();
        itemIOList.add(itemIO);

        itemIO = ItemIO
                .builder()
                .id("paper")
                .name("paper")
                .description("paper description")
                .price(new BigDecimal("0.10"))
                .build();
        itemIOList.add(itemIO);

        Set<String> itemIdSet = ItemMapper.MAPPER.map(itemIOList);
        itemIOList.stream().forEach(item -> assertThat(itemIdSet.contains(item.getId()), is(true)));
    }
}