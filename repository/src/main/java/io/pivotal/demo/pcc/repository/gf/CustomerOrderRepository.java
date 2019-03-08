package io.pivotal.demo.pcc.repository.gf;

import io.pivotal.demo.pcc.model.gf.CustomerOrder;
import org.springframework.data.gemfire.repository.GemfireRepository;
import org.springframework.data.gemfire.repository.Query;
import org.springframework.data.gemfire.repository.query.annotation.Trace;

import java.time.LocalDate;
import java.util.List;

public interface CustomerOrderRepository extends GemfireRepository<CustomerOrder, String> {
    @Trace
    @Query("SELECT DISTINCT * FROM /customer-order" +
            " WHERE customerId = $1")
    List<CustomerOrder> findByCustomerId(String customerId);

    @Trace
    @Query("SELECT id FROM /customer-order" +
            " WHERE customerId = $1 AND orderDate >= $2 AND orderDate < $3")
    List<String> findIdsByOrderDateRange(String customerId, long beginDate, long endDate);
}
