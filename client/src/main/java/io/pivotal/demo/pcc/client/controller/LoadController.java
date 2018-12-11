package io.pivotal.demo.pcc.client.controller;

import io.pivotal.demo.pcc.client.function.CustomerOrderFunction;
import io.pivotal.demo.pcc.model.gf.pdx.Customer;
import io.pivotal.demo.pcc.model.gf.pdx.CustomerOrder;
import io.pivotal.demo.pcc.model.gf.pdx.Item;
import io.pivotal.demo.pcc.model.io.CustomerOrderIO;
import io.pivotal.demo.pcc.repository.gf.CustomerOrderRepository;
import io.pivotal.demo.pcc.repository.gf.CustomerRepository;
import io.pivotal.demo.pcc.repository.gf.ItemRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@Slf4j
@AllArgsConstructor
@RestController
public class LoadController {
    private final CustomerRepository customerRepository;

    private final CustomerOrderRepository customerOrderRepository;

    private final ItemRepository itemRepository;

    private final CustomerOrderFunction customerOrderFunction;


    @RequestMapping(value = "/clearData", method = RequestMethod.POST)
    public void clearDate() {
        customerRepository.deleteAll(customerRepository.findAll());
        customerOrderRepository.deleteAll(customerOrderRepository.findAll());
        itemRepository.deleteAll(itemRepository.findAll());
    }

    @RequestMapping(value = "/loadDataByRegionPutAll", method = RequestMethod.POST)
    public void loadDataByRegionPutAll() {
        clearDate();

        Customer customer1 = Customer
                .builder()
                .id("customer1")
                .name("Krikor Garegin")
                .build();
        customerRepository.save(customer1);

        Customer customer2 = Customer
                .builder()
                .id("customer2")
                .name("Ararat Avetis")
                .build();
        customerRepository.save(customer2);

        Item pencil = Item
                .builder()
                .id("pencil")
                .name("pencil")
                .description("pencil decription")
                .price("0.99")
                .build();
        itemRepository.save(pencil);

        Item pen = Item
                .builder()
                .id("pen")
                .name("pen")
                .description("pen description")
                .price("1.49")
                .build();
        itemRepository.save(pen);

        Item paper = Item
                .builder()
                .id("paper")
                .name("paper")
                .description("paper description")
                .price("0.10")
                .build();
        itemRepository.save(paper);

        Map<String, CustomerOrder> dataMap = new HashMap<>();
        Set<String> itemSet = new HashSet<>();
        itemSet.add(pen.getId());
        itemSet.add(paper.getId());
        // 1.49 + 0.10 = 1.59
        CustomerOrder customerOrder = CustomerOrder
                .builder()
                .collocatedId(customer1.getId() + "|" + "order1")
                .id("order1")
                .customerId(customer1.getId())
                .shippingAddress("address1")
                .orderDate(new Date().getTime())
                .items(itemSet)
                .build();
        dataMap.put(customerOrder.getCollocatedId(), customerOrder);

        itemSet = new HashSet<>();
        itemSet.add(pencil.getId());
        itemSet.add(pen.getId());
        itemSet.add(paper.getId());
        // 1.59 + 0.99 = 2.58
        customerOrder = CustomerOrder
                .builder()
                .collocatedId(customer1.getId() + "|" + "order2")
                .id("order2")
                .customerId(customer1.getId())
                .shippingAddress("address1")
                .orderDate(new Date().getTime())
                .items(itemSet)
                .build();
        dataMap.put(customerOrder.getCollocatedId(), customerOrder);

        itemSet = new HashSet<>();
        itemSet.add(pencil.getId());
        itemSet.add(pen.getId());
        // 0.99 + 1.49 = 2.48
        customerOrder = CustomerOrder
                .builder()
                .collocatedId(customer2.getId() + "|" + "order3")
                .id("order3")
                .customerId(customer2.getId())
                .shippingAddress("address2")
                .orderDate(new Date().getTime())
                .items(itemSet)
                .build();
        dataMap.put(customerOrder.getCollocatedId(), customerOrder);
        customerOrderRepository.saveAll(dataMap.values());
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/getCustomerOrderList", method = RequestMethod.GET)
    public Iterable<CustomerOrderIO> getCustomerOrderList(@RequestParam(value = "customerId") String customerId) {
        Set<String> filter = new HashSet<>();
        filter.add(customerId + "|");

        return customerOrderFunction.getCustomerOrderList(filter);
    }

}
