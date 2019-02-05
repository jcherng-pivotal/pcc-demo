package io.pivotal.demo.pcc.repository.gf;

import io.pivotal.demo.pcc.model.gf.Customer;
import org.springframework.data.gemfire.repository.GemfireRepository;

public interface CustomerRepository extends GemfireRepository<Customer, String> {

}
