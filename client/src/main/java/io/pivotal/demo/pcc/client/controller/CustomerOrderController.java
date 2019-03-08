package io.pivotal.demo.pcc.client.controller;

import io.pivotal.demo.pcc.client.exception.ResourceNotFoundException;
import io.pivotal.demo.pcc.model.gf.CustomerOrder;
import io.pivotal.demo.pcc.model.gf.Item;
import io.pivotal.demo.pcc.model.io.CustomerOrderIO;
import io.pivotal.demo.pcc.model.mapper.CustomerOrderMapper;
import io.pivotal.demo.pcc.model.mapper.ItemMapper;
import io.pivotal.demo.pcc.repository.gf.CustomerOrderRepository;
import io.pivotal.demo.pcc.repository.gf.ItemRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/customers")
public class CustomerOrderController {
    private final CustomerOrderRepository customerOrderRepository;
    private final ItemRepository itemRepository;

    @GetMapping("/{customerId}/orders/{id}")
    public CustomerOrderIO findCustomerOrder(@PathVariable String customerId,
                                             @PathVariable String id) {
        CustomerOrder customerOrder = customerOrderRepository
                .findById(customerId + "|" + id)
                .orElseThrow(() -> new ResourceNotFoundException());
        return getCustomerOrderIO(customerOrder);
    }

    @GetMapping("/{customerId}/orders/")
    public List<CustomerOrderIO> findCustomerOrders(@PathVariable String customerId,
                                                    @RequestParam(value = "ids", required = false)
                                                            List<String> ids) {
        List<String> collocatedIds = ids
                .stream()
                .map(id -> customerId + "|" + id)
                .collect(Collectors.toList());
        List<CustomerOrder> customerOrders;
        if (ids != null) {
            customerOrders = StreamSupport
                    .stream(customerOrderRepository.findAllById(collocatedIds).spliterator(), false)
                    .collect(Collectors.toList());
        } else {
            customerOrders = customerOrderRepository.findByCustomerId(customerId);
        }

        return customerOrders
                .stream()
                .map(customerOrder -> getCustomerOrderIO(customerOrder))
                .collect(Collectors.toList());
    }

    @GetMapping("/{customerId}/orders/ids/")
    public List<String> findIdsByOrderDateRange(@PathVariable String customerId,
                                                @RequestParam("beginDate")
                                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                        LocalDate beginDate,
                                                @RequestParam("endDate")
                                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                        LocalDate endDate) {
        long beginEpochMilli = beginDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long endEpochMilli = endDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
        return customerOrderRepository.findIdsByOrderDateRange(customerId, beginEpochMilli, endEpochMilli);
    }

    @PutMapping("/{customerId}/orders/{id}")
    public void updateCustomerOrder(@PathVariable String customerId,
                                    @PathVariable String id,
                                    @RequestBody CustomerOrderIO customerOrderIO) {
        CustomerOrder customerOrder = CustomerOrderMapper.MAPPER.map(customerOrderIO);
        customerOrderRepository.save(customerOrder);
    }

    private CustomerOrderIO getCustomerOrderIO(CustomerOrder customerOrder) {
        CustomerOrderIO customerOrderIO = CustomerOrderMapper.MAPPER.map(customerOrder);
        Iterable<Item> items = itemRepository.findAllById(customerOrder.getItems());
        customerOrderIO.setItems(ItemMapper.MAPPER.map(items));
        return customerOrderIO;
    }
}
