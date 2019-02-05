package io.pivotal.demo.pcc.client.controller;

import io.pivotal.demo.pcc.model.gf.Customer;
import io.pivotal.demo.pcc.model.gf.CustomerOrder;
import io.pivotal.demo.pcc.model.gf.Item;
import io.pivotal.demo.pcc.model.io.CustomerOrderIO;
import io.pivotal.demo.pcc.repository.gf.CustomerOrderRepository;
import io.pivotal.demo.pcc.repository.gf.CustomerRepository;
import io.pivotal.demo.pcc.repository.gf.ItemRepository;
import io.pivotal.demo.pcc.server.function.CustomerOrderListFunction;
import org.apache.geode.cache.DataPolicy;
import org.apache.geode.cache.GemFireCache;
import org.apache.geode.cache.Region;
import org.springframework.data.gemfire.function.execution.GemfireOnRegionFunctionTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class LoadController {

    private final GemFireCache gemFireCache;

    private final CustomerRepository customerRepository;

    private final CustomerOrderRepository customerOrderRepository;

    private final ItemRepository itemRepository;

    private final Region<String, Customer> customerRegion;

    private final Region<String, CustomerOrder> customerOrderRegion;

    private final Region<String, Item> itemRegion;

    private final GemfireOnRegionFunctionTemplate customerOrderRegionFunctionTemplate;

    public LoadController(GemFireCache gemFireCache,
                          CustomerRepository customerRepository,
                          CustomerOrderRepository customerOrderRepository,
                          ItemRepository itemRepository,
                          GemfireOnRegionFunctionTemplate customerOrderRegionFunctionTemplate) {
        this.gemFireCache = gemFireCache;
        this.customerRepository = customerRepository;
        this.customerOrderRepository = customerOrderRepository;
        this.itemRepository = itemRepository;

        this.customerOrderRegionFunctionTemplate = customerOrderRegionFunctionTemplate;

        customerRegion = gemFireCache.getRegion("customer");
        customerOrderRegion = gemFireCache.getRegion("customer-order");
        itemRegion = gemFireCache.getRegion("item");
    }

    @RequestMapping(value = "/clearData", method = RequestMethod.POST)
    public void clearDate() {
        Set<String> customerKeySet = (DataPolicy.EMPTY.equals(customerRegion.getAttributes().getDataPolicy()))
                ? customerRegion.keySetOnServer() : customerRegion.keySet();
        Set<String> customerOrderKeySet = (DataPolicy.EMPTY
                .equals(customerOrderRegion.getAttributes().getDataPolicy())) ? customerOrderRegion.keySetOnServer()
                : customerOrderRegion.keySet();
        Set<String> itemKeySet = (DataPolicy.EMPTY.equals(itemRegion.getAttributes().getDataPolicy()))
                ? itemRegion.keySetOnServer() : itemRegion.keySet();

        customerRegion.removeAll(customerKeySet);
        customerOrderRegion.removeAll(customerOrderKeySet);
        itemRegion.removeAll(itemKeySet);
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
        return customerOrderRegionFunctionTemplate
                .execute(CustomerOrderListFunction.class.getSimpleName(), filter);
    }

}
