package io.pivotal.demo.pcc.loader.controller;

import io.pivotal.demo.pcc.loader.scheduling.LoadServiceScheduler;
import io.pivotal.demo.pcc.model.constant.FunctionName;
import io.pivotal.demo.pcc.model.io.CustomerOrderIO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.gemfire.function.execution.GemfireOnRegionFunctionTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/data-load")
public class LoadController {
    private final LoadServiceScheduler loadServiceScheduler;
    private final GemfireOnRegionFunctionTemplate customerOrderRegionFunctionTemplate;

    @PostMapping("/loadAll")
    public void loadAll() {
        loadServiceScheduler.loadAll();
    }

    @SuppressWarnings("unchecked")
    @GetMapping("/getCustomerOrderList")
    public Iterable<CustomerOrderIO> getCustomerOrderList(@RequestParam(value = "customerId") String customerId) {
        Set<String> filter = new HashSet<>();
        filter.add(customerId + "|");
        return customerOrderRegionFunctionTemplate
                .execute(FunctionName.CUSTOMER_ORDER_LIST_FUNCTION, filter);
    }

    @PostConstruct
    public void init() {
        loadAll();
    }
}
