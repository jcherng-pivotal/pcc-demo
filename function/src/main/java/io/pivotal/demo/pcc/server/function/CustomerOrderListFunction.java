package io.pivotal.demo.pcc.server.function;

import io.pivotal.demo.pcc.model.gf.Customer;
import io.pivotal.demo.pcc.model.gf.CustomerOrder;
import io.pivotal.demo.pcc.model.gf.Item;
import io.pivotal.demo.pcc.model.io.CustomerIO;
import io.pivotal.demo.pcc.model.io.CustomerOrderIO;
import io.pivotal.demo.pcc.model.io.ItemIO;
import org.apache.geode.cache.CacheFactory;
import org.apache.geode.cache.GemFireCache;
import org.apache.geode.cache.Region;
import org.apache.geode.cache.execute.FunctionException;
import org.apache.geode.cache.execute.RegionFunctionContext;
import org.apache.geode.cache.query.Query;
import org.apache.geode.cache.query.QueryService;
import org.apache.geode.cache.query.SelectResults;
import org.apache.geode.pdx.PdxInstance;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class CustomerOrderListFunction implements DataAwareFunction {

    private final GemFireCache cache;

    private Region<String, PdxInstance> customerRegion;

    private Region<String, PdxInstance> customerOrderRegion;

    private Region<String, PdxInstance> itemRegion;

    private Boolean areRegionsInitialized = false;

    public CustomerOrderListFunction() {
        this(CacheFactory.getAnyInstance());
    }

    public CustomerOrderListFunction(GemFireCache cache) {
        super();
        this.cache = cache;
        initializeRegions();
    }

    private void initializeRegions() {
        if (cache != null && !areRegionsInitialized) {
            customerRegion = cache.getRegion("customer");
            customerOrderRegion = cache.getRegion("customer-order");
            itemRegion = cache.getRegion("item");

            if (customerRegion != null && customerOrderRegion != null && itemRegion != null) {
                areRegionsInitialized = true;
            }
        }
    }

    @Override
    public boolean validateFilters(Set<?> filters) {
        Optional.ofNullable(filters).filter(s -> !s.isEmpty())
                .map(s -> s.stream().filter(v -> !(v instanceof String)).count()).filter(count -> count == 0)
                .orElseThrow(() -> new IllegalArgumentException("invalid filters for CustomerOrderListFunction"));
        return true;
    }

    @Override
    public boolean validateRequest(Object request) {
        // request validation is not required for this function
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void process(RegionFunctionContext regionFunctionContext) {
        initializeRegions();
        QueryService queryService = cache.getQueryService();
        String qstr = "SELECT * FROM /customer-order";

        try {
            Query query = queryService.newQuery(qstr);
            SelectResults<PdxInstance> results = (SelectResults<PdxInstance>) query
                    .execute(regionFunctionContext);
            List<CustomerOrder> entryList = results
                    .stream()
                    .map(pdxInstance -> new CustomerOrder(pdxInstance))
                    .collect(Collectors.toList());

            CustomerOrderIO customerOrderIO = null;
            for (CustomerOrder customerOrder : entryList) {
                if (customerOrderIO != null) {
                    regionFunctionContext.getResultSender().sendResult(customerOrderIO);
                }

                if (regionFunctionContext.getFilter().contains(customerOrder.getCustomerId() + "|")) {
                    Customer customer = new Customer(customerRegion.get(customerOrder.getCustomerId()));
                    CustomerIO customerIO = new CustomerIO(customer.getId(), customer.getName());

                    customerOrderIO = new CustomerOrderIO();
                    customerOrderIO.setId(customerOrder.getId());
                    customerOrderIO.setShippingAddress(customerOrder.getShippingAddress());
                    customerOrderIO.setOrderDate(new Date(customerOrder.getOrderDate()));

                    Set<ItemIO> itemIOSet = new HashSet<>();
                    Set<String> itemSet = customerOrder.getItems();
                    for (String itemId : itemSet) {
                        Item item = new Item(itemRegion.get(itemId));
                        ItemIO itemIO = new ItemIO(itemId, item.getName(), item.getDescription(),
                                new BigDecimal(item.getPrice()));

                        itemIOSet.add(itemIO);
                    }
                    customerOrderIO.setItems(itemIOSet);
                }
            }

            // Send the result to function caller node.
            regionFunctionContext.getResultSender().lastResult(customerOrderIO);

        } catch (Exception e) {
            throw new FunctionException(e);
        }
    }

}
