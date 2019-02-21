package io.pivotal.demo.pcc.loader.service;

import io.pivotal.demo.pcc.model.gf.CustomerOrder;
import io.pivotal.demo.pcc.model.jpa.CustomerOrderEntity;
import io.pivotal.demo.pcc.model.mapper.CustomerOrderEntityMapper;
import io.pivotal.demo.pcc.repository.gf.CustomerOrderRepository;
import io.pivotal.demo.pcc.repository.jpa.CustomerEntityRepository;
import io.pivotal.demo.pcc.repository.jpa.CustomerOrderEntityRepository;
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
public class CustomerOrderLoadService implements PagingLoadService<CustomerOrderEntity, CustomerOrder> {
    private final CustomerOrderEntityRepository sourceRepository;
    private final CustomerOrderRepository targetRepository;
    private final Sort defaultSort = CustomerEntityRepository.DEFAULT_SORT;
    private final CustomerOrderEntityMapper mapper = CustomerOrderEntityMapper.MAPPER;
    private final GemfireOnRegionFunctionTemplate customerOrderRegionFunctionTemplate;


    @Override
    public Sort getDefaultSort() {
        return defaultSort;
    }

    @Override
    public CustomerOrder map(CustomerOrderEntity source) {
        return mapper.map(source);
    }

    @Override
    public GemfireOnRegionFunctionTemplate getRegionFunctionTemplate() {
        return customerOrderRegionFunctionTemplate;
    }
}
