package io.pivotal.demo.pcc.model.mapper;

import io.pivotal.demo.pcc.model.gf.Item;
import io.pivotal.demo.pcc.model.io.ItemIO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.HashSet;
import java.util.Set;

@Mapper
public interface ItemMapper {

    ItemMapper MAPPER = Mappers.getMapper(ItemMapper.class);

    Item map(ItemIO source);

    ItemIO map(Item source);

    Iterable<ItemIO> map(Iterable<Item> source);

    default Set<String> mapIds(Iterable<ItemIO> source) {
        Set<String> target = new HashSet<>();
        source.forEach(itemIO -> target.add(itemIO.getId()));
        return target;
    }
}
