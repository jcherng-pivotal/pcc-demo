package io.pivotal.demo.pcc.loader.service;

import io.pivotal.demo.pcc.model.gf.Customer;
import io.pivotal.demo.pcc.model.jpa.CustomerEntity;
import io.pivotal.demo.pcc.model.mapper.CustomerEntityMapper;
import io.pivotal.demo.pcc.repository.gf.CustomerRepository;
import io.pivotal.demo.pcc.repository.jpa.CustomerEntityRepository;
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
public class CustomerLoadService implements PagingLoadService<CustomerEntity, Customer> {
    private final CustomerEntityRepository sourceRepository;
    private final CustomerRepository targetRepository;
    private final Sort defaultSort = CustomerEntityRepository.DEFAULT_SORT;
    private final CustomerEntityMapper mapper = CustomerEntityMapper.MAPPER;
    private final GemfireOnRegionFunctionTemplate customerRegionFunctionTemplate;


    @Override
    public Sort getDefaultSort() {
        return defaultSort;
    }

    @Override
    public Customer map(CustomerEntity source) {
        return mapper.map(source);
    }

    @Override
    public GemfireOnRegionFunctionTemplate getRegionFunctionTemplate() {
        return customerRegionFunctionTemplate;
    }
}
