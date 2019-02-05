package io.pivotal.demo.pcc.server.function;

import io.pivotal.demo.pcc.model.gf.CustomerOrder;
import io.pivotal.demo.pcc.model.gf.Item;
import org.apache.geode.cache.CacheFactory;
import org.apache.geode.cache.GemFireCache;
import org.apache.geode.cache.Region;
import org.apache.geode.cache.execute.Function;
import org.apache.geode.cache.execute.FunctionContext;
import org.apache.geode.cache.execute.FunctionException;
import org.apache.geode.cache.execute.RegionFunctionContext;
import org.apache.geode.cache.query.Query;
import org.apache.geode.cache.query.QueryService;
import org.apache.geode.cache.query.SelectResults;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public class CustomerOrderPriceFunction implements Function {
    private final GemFireCache cache;

    private Region<String, CustomerOrder> customerOrderRegion;

    private Region<String, Item> itemRegion;

    private Boolean areRegionsInitialized = false;

    public CustomerOrderPriceFunction() {
        this(CacheFactory.getAnyInstance());
    }

    public CustomerOrderPriceFunction(GemFireCache cache) {
        super();
        this.cache = cache;
        this.customerOrderRegion = cache.getRegion("customer-order");
        this.itemRegion = cache.getRegion("item");
    }

    private void initializeRegions() {
        if (cache != null && !areRegionsInitialized) {
            customerOrderRegion = cache.getRegion("customer-order");
            itemRegion = cache.getRegion("item");

            if (customerOrderRegion != null && itemRegion != null) {
                areRegionsInitialized = true;
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void execute(FunctionContext fc) {
        initializeRegions();
        if (!(fc instanceof RegionFunctionContext)) {
            throw new FunctionException(
                    "This is a data aware function, and has to be called using FunctionService.onRegion.");
        }
        RegionFunctionContext rfc = (RegionFunctionContext) fc;
        QueryService queryService = customerOrderRegion.getRegionService().getQueryService();
        String qstr = "SELECT * FROM /customer-order";

        try {
            Query query = queryService.newQuery(qstr);
            SelectResults<CustomerOrder> results = (SelectResults<CustomerOrder>) query
                    .execute(rfc);
            List<CustomerOrder> entryList = results.asList();

            BigDecimal totalPrice = null;
            for (CustomerOrder customerOrder : entryList) {
                if (totalPrice == null) {
                    totalPrice = new BigDecimal("0.00");
                }

                if (rfc.getFilter().contains(customerOrder.getCustomerId())) {
                    Set<String> itemSet = customerOrder.getItems();
                    for (String itemId : itemSet) {
                        Item item = itemRegion.get(itemId);
                        totalPrice = totalPrice.add(new BigDecimal(item.getPrice()));
                    }
                }
            }

            // Send the result to function caller node.
            fc.getResultSender().lastResult(totalPrice);

        } catch (Exception e) {
            throw new FunctionException(e);
        }
    }

    @Override
    public String getId() {
        return this.getClass().getSimpleName();
    }

    @Override
    public boolean hasResult() {
        return true;
    }

    @Override
    public boolean isHA() {
        return false;
    }

    @Override
    public boolean optimizeForWrite() {
        return true;
    }

}
