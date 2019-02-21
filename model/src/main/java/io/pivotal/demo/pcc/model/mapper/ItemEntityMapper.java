package io.pivotal.demo.pcc.model.mapper;

import io.pivotal.demo.pcc.model.gf.Item;
import io.pivotal.demo.pcc.model.jpa.ItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.HashSet;
import java.util.Set;

@Mapper
public interface ItemEntityMapper {

    ItemEntityMapper MAPPER = Mappers.getMapper(ItemEntityMapper.class);

    Item map(ItemEntity source);

    Iterable<Item> map(Iterable<ItemEntity> source);

    default Set<String> mapIds(Iterable<ItemEntity> source) {
        Set<String> target = new HashSet<>();
        source.forEach(itemEntity -> target.add(itemEntity.getId()));
        return target;
    }
}
