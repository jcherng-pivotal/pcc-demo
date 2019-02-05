package io.pivotal.demo.pcc.client.controller;

import io.pivotal.demo.pcc.client.exception.ResourceNotFoundException;
import io.pivotal.demo.pcc.model.gf.Customer;
import io.pivotal.demo.pcc.model.io.CustomerIO;
import io.pivotal.demo.pcc.model.mapper.CustomerMapper;
import io.pivotal.demo.pcc.repository.gf.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerRepository customerRepository;

    @GetMapping("/{id}")
    public CustomerIO getCustomerById(@PathVariable String id) {
        Customer customer = customerRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException());
        return CustomerMapper.MAPPER.getCustomerIO(customer);
    }

    @PutMapping("/{id}")
    public void updateCustomerById(@PathVariable String id,
                                   @RequestBody CustomerIO customerIO) {
        Customer customer = CustomerMapper.MAPPER.getCustomer(customerIO);
        customerRepository.save(customer);
    }
}
