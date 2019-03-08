package io.pivotal.demo.pcc.client.controller;

import com.google.common.collect.Lists;
import io.pivotal.demo.pcc.client.service.CustomerOrderService;
import io.pivotal.demo.pcc.model.io.CustomerOrderIO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/customers")
public class CustomerOrderController {

    CustomerOrderService customerOrderService;

    @GetMapping("/{customerId}/orders/")
    public Page<CustomerOrderIO> findCustomerOrders(@PathVariable String customerId,
                                                    @RequestParam("beginDate")
                                                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                            LocalDate beginDate,
                                                    @RequestParam("endDate")
                                                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                            LocalDate endDate,
                                                    @RequestParam("page") int page,
                                                    @RequestParam("size") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<String> ids = findIdsByOrderDateRange(customerId, beginDate, endDate, pageable);
        return findAllByIds(customerId, ids, beginDate, endDate);
    }

    private Page<String> findIdsByOrderDateRange(String customerId,
                                                 LocalDate beginDate,
                                                 LocalDate endDate,
                                                 Pageable pageable) {
        List<String> ids = Lists.newArrayList();
        for (int year = beginDate.getYear(); year <= endDate.getYear(); year++) {
            ids.addAll(customerOrderService.findIdsByOrderDateRange(customerId, beginDate, endDate, year));
        }
        int totalCount = ids.size();
        int offset = Math.toIntExact(pageable.getOffset());
        int end = offset + pageable.getPageSize();
        int fromIndex = totalCount >= offset ? offset : -1;
        int toIndex = totalCount >= end ? end : totalCount;
        List<String> subList;
        if (fromIndex == -1 || fromIndex > toIndex) {
            subList = Lists.newArrayList();
        } else {
            subList = ids.subList(fromIndex, toIndex);
        }
        return new PageImpl<>(subList, pageable, totalCount);
    }

    private Page<CustomerOrderIO> findAllByIds(String customerId, Page<String> ids, LocalDate beginDate, LocalDate endDate) {
        List<CustomerOrderIO> customerOrderIOs = Lists.newArrayList();
        if (!ids.isEmpty()) {
            for (int year = beginDate.getYear(); year <= endDate.getYear(); year++) {
                customerOrderIOs.addAll(customerOrderService.findCustomerOrders(customerId, ids.getContent(), year));
            }
        }
        return new PageImpl<>(customerOrderIOs, ids.getPageable(), ids.getTotalElements());
    }

}
