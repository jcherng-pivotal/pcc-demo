package io.pivotal.demo.pcc.client.function;

import org.springframework.data.gemfire.function.annotation.Filter;
import org.springframework.data.gemfire.function.annotation.FunctionId;
import org.springframework.data.gemfire.function.annotation.OnRegion;

import java.util.Set;

@OnRegion(region = "customer-order")
public interface CustomerOrderFunction {

    @FunctionId("CustomerOrderListFunction")
    Iterable getCustomerOrderList(@Filter Set<String> filter);
}
