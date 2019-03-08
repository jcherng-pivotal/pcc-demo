package io.pivotal.demo.pcc.client.client;

import io.pivotal.demo.pcc.model.io.CustomerOrderIO;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

public interface CustomerOrderClient {
    @GetMapping("/customers/{customerId}/orders/{id}")
    CustomerOrderIO findCustomerOrder(@PathVariable String customerId,
                                      @PathVariable String id);

    @GetMapping("/customers/{customerId}/orders/")
    List<CustomerOrderIO> findCustomerOrders(@PathVariable String customerId,
                                             @RequestParam(value = "ids", required = false)
                                                     List<String> ids);

    @GetMapping("/customers/{customerId}/orders/ids/")
    List<String> findIdsByOrderDateRange(@PathVariable String customerId,
                                         @RequestParam("beginDate")
                                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                 LocalDate beginDate,
                                         @RequestParam("endDate")
                                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                 LocalDate endDate);

    @PutMapping("/customers/{customerId}/orders/{id}")
    void updateCustomerOrder(@PathVariable String customerId,
                             @PathVariable String id,
                             @RequestBody CustomerOrderIO customerOrderIO);
}