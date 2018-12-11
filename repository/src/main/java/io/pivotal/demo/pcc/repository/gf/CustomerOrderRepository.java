package io.pivotal.demo.pcc.repository.gf;

import io.pivotal.demo.pcc.model.gf.pdx.CustomerOrder;
import org.springframework.data.gemfire.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerOrderRepository extends CrudRepository<CustomerOrder, String> {
    @Query("<TRACE> SELECT DISTINCT * FROM /customer-order x" +
            " WHERE customerId = $1")
    List<CustomerOrder> findByCustomerId(String customerId);
}
