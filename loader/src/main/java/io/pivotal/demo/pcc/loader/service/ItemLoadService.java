package io.pivotal.demo.pcc.loader.service;

import io.pivotal.demo.pcc.model.gf.Item;
import io.pivotal.demo.pcc.model.jpa.ItemEntity;
import io.pivotal.demo.pcc.model.mapper.ItemEntityMapper;
import io.pivotal.demo.pcc.repository.gf.ItemRepository;
import io.pivotal.demo.pcc.repository.jpa.ItemEntityRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.gemfire.function.execution.GemfireOnRegionFunctionTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Getter
@Service
public class ItemLoadService implements PagingLoadService<ItemEntity, Item> {
    private final ItemEntityRepository sourceRepository;
    private final ItemRepository targetRepository;
    private final Sort defaultSort = ItemEntityRepository.DEFAULT_SORT;
    private final ItemEntityMapper mapper = ItemEntityMapper.MAPPER;
    private final GemfireOnRegionFunctionTemplate itemRegionFunctionTemplate;


    @Override
    public Sort getDefaultSort() {
        return defaultSort;
    }

    @Override
    public Item map(ItemEntity source) {
        return mapper.map(source);
    }

    @Override
    public GemfireOnRegionFunctionTemplate getRegionFunctionTemplate() {
        return itemRegionFunctionTemplate;
    }
}
