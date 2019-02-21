package io.pivotal.demo.pcc.server.config;

import io.pivotal.demo.pcc.model.gf.Customer;
import io.pivotal.demo.pcc.model.gf.CustomerOrder;
import io.pivotal.demo.pcc.model.gf.Item;
import io.pivotal.demo.pcc.server.function.CustomerOrderListFunction;
import io.pivotal.demo.pcc.server.function.CustomerOrderPriceFunction;
import io.pivotal.demo.pcc.server.security.TestSecurityManager;
import org.apache.geode.cache.GemFireCache;
import org.apache.geode.cache.PartitionAttributesFactory;
import org.apache.geode.cache.RegionAttributes;
import org.apache.geode.cache.execute.Function;
import org.apache.geode.cache.util.StringPrefixPartitionResolver;
import org.apache.geode.pdx.PdxSerializer;
import org.apache.geode.pdx.ReflectionBasedAutoSerializer;
import org.apache.geode.security.SecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.gemfire.PartitionedRegionFactoryBean;
import org.springframework.data.gemfire.RegionAttributesFactoryBean;
import org.springframework.data.gemfire.config.annotation.CacheServerApplication;
import org.springframework.data.gemfire.config.annotation.EnableLocator;
import org.springframework.data.gemfire.config.annotation.EnableManager;
import org.springframework.data.gemfire.config.annotation.EnablePdx;
import org.springframework.data.gemfire.function.FunctionServiceFactoryBean;

import java.util.Arrays;

@CacheServerApplication(name = "ServerApplication")
@EnableLocator
@EnableManager
//simulating PCC's pdx serializer configuration where readSerialized is set to true
@EnablePdx(serializerBeanName = "reflectionBasedAutoSerializer", readSerialized = true)
public class GemFireConfig {

    @Bean
    SecurityManager testSecurityManager(Environment environment) {
        return new TestSecurityManager(environment);
    }

    @Bean
    PdxSerializer reflectionBasedAutoSerializer(){
        // PCC's ReflectionBasedAutoSerializer is not configured with regex for classes
        PdxSerializer pdxSerializer = new ReflectionBasedAutoSerializer();
        return pdxSerializer;
    }

    @Bean("customer")
    PartitionedRegionFactoryBean<String, Customer> customerRegion(final GemFireCache cache) {
        PartitionedRegionFactoryBean<String, Customer> customerRegion = new PartitionedRegionFactoryBean<>();
        customerRegion.setCache(cache);
        customerRegion.setClose(false);
        customerRegion.setName("customer");
        return customerRegion;
    }

    @Bean
    RegionAttributesFactoryBean customerOrderRegionAttributes() {
        RegionAttributesFactoryBean customerOrderRegionAttributes = new RegionAttributesFactoryBean();
        customerOrderRegionAttributes
                .setPartitionAttributes(new PartitionAttributesFactory<String, Object>()
                        .setPartitionResolver(new StringPrefixPartitionResolver())
                        .setColocatedWith("customer")
                        .create());
        return customerOrderRegionAttributes;
    }

    @Bean("customer-order")
    PartitionedRegionFactoryBean<String, CustomerOrder> customerOrderRegion(final GemFireCache cache,
                                                                            @Qualifier("customerOrderRegionAttributes")
                                                                                    RegionAttributes<String, CustomerOrder> customerOrderRegionAttributes) {
        PartitionedRegionFactoryBean<String, CustomerOrder> customerOrderRegion = new PartitionedRegionFactoryBean<>();
        customerOrderRegion.setCache(cache);
        customerOrderRegion.setClose(false);
        customerOrderRegion.setName("customer-order");
        customerOrderRegion.setAttributes(customerOrderRegionAttributes);
        return customerOrderRegion;
    }

    @Bean("item")
    PartitionedRegionFactoryBean<String, Item> itemRegion(final GemFireCache cache) {
        PartitionedRegionFactoryBean<String, Item> itemRegion = new PartitionedRegionFactoryBean<>();
        itemRegion.setCache(cache);
        itemRegion.setClose(false);
        itemRegion.setName("item");
        return itemRegion;
    }

    @Bean
    FunctionServiceFactoryBean functionService(final Function customerOrderPriceFunction,
                                               final Function customerOrderListFunction) {
        FunctionServiceFactoryBean functionService = new FunctionServiceFactoryBean();
        functionService
                .setFunctions(Arrays.asList(new Function[]{customerOrderPriceFunction, customerOrderListFunction}));
        return functionService;
    }

    @Bean
    Function customerOrderPriceFunction(GemFireCache cache) {
        CustomerOrderPriceFunction customerOrderPriceFunction = new CustomerOrderPriceFunction(cache);
        return customerOrderPriceFunction;
    }

    @Bean
    Function customerOrderListFunction(GemFireCache cache) {
        CustomerOrderListFunction customerOrderListFunction = new CustomerOrderListFunction(cache);
        return customerOrderListFunction;
    }

}
