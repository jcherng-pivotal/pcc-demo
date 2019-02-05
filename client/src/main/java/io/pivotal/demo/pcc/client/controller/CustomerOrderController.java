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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/customers")
public class CustomerOrderController {
    private final CustomerOrderRepository customerOrderRepository;
    private final ItemRepository itemRepository;

    @GetMapping("/{customerId}/orders/")
    public List<CustomerOrderIO> getCustomerOrdersByCustomerId(@PathVariable String customerId) {
        List<CustomerOrder> customerOrders = customerOrderRepository.findByCustomerId(customerId);
        return customerOrders
                .stream()
                .map(customerOrder -> getCustomerOrderIO(customerOrder))
                .collect(Collectors.toList());
    }

    @GetMapping("/{customerId}/orders/{id}")
    public CustomerOrderIO getCustomerOrderByCustomerIdAndId(@PathVariable String customerId,
                                                             @PathVariable String id) {
        CustomerOrder customerOrder = customerOrderRepository
                .findById(customerId + "|" + id)
                .orElseThrow(() -> new ResourceNotFoundException());
        return getCustomerOrderIO(customerOrder);
    }

    @PutMapping("/{customerId}/orders/{id}")
    public void updateCustomerOrderByCustomerIdAndId(@PathVariable String customerId,
                                                     @PathVariable String id,
                                                     @RequestBody CustomerOrderIO customerOrderIO) {
        CustomerOrder customerOrder = CustomerOrderMapper.MAPPER.getCustomerOrder(customerOrderIO);
        customerOrderRepository.save(customerOrder);
    }

    private CustomerOrderIO getCustomerOrderIO(CustomerOrder customerOrder) {
        CustomerOrderIO customerOrderIO = CustomerOrderMapper.MAPPER.getCustomerOrderIO(customerOrder);
        Iterable<Item> items = itemRepository.findAllById(customerOrder.getItems());
        customerOrderIO.setItems(ItemMapper.MAPPER.getItemIOs(items));
        return customerOrderIO;
    }
}
