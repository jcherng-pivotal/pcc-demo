package io.pivotal.demo.pcc.client.service;

import io.pivotal.demo.pcc.client.client.CustomerOrderClient;
import io.pivotal.demo.pcc.model.io.CustomerOrderIO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class CustomerOrderService {

    ApplicationContext applicationContext;

    @Cacheable("customer-ids-by-search-request")
    public List<String> findIdsByOrderDateRange(String customerId, LocalDate beginDate, LocalDate endDate, int year) {
        try {
            CustomerOrderClient customerOrderClient = (CustomerOrderClient) applicationContext.getBean("customer-order-service-" + year);
            return customerOrderClient.findIdsByOrderDateRange(customerId, beginDate, endDate);
        } catch (RuntimeException e) {
            log.warn(e.getMessage());
            return new ArrayList<>();
        }
    }

    public List<CustomerOrderIO> findCustomerOrders(String customerId, List<String> ids, int year) {
        try {
            CustomerOrderClient customerOrderClient = (CustomerOrderClient) applicationContext.getBean("customer-order-service-" + year);
            return customerOrderClient.findCustomerOrders(customerId, ids);
        } catch (RuntimeException e) {
            log.warn(e.getMessage());
            return new ArrayList<>();
        }
    }
}
