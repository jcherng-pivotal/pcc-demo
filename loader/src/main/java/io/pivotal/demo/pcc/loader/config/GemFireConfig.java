package io.pivotal.demo.pcc.loader.config;

import io.pivotal.demo.pcc.model.gf.Customer;
import io.pivotal.demo.pcc.model.gf.CustomerOrder;
import io.pivotal.demo.pcc.model.gf.Item;
import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.ClientRegionShortcut;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.config.annotation.EnableEntityDefinedRegions;
import org.springframework.data.gemfire.function.execution.GemfireOnRegionFunctionTemplate;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;

@Configuration
@EnableEntityDefinedRegions(basePackages = {"io.pivotal.demo.pcc.model.gf"},
        clientRegionShortcut = ClientRegionShortcut.PROXY)
@EnableGemfireRepositories(basePackages = {"io.pivotal.demo.pcc.repository.gf"})
public class GemFireConfig {

    @Bean
    GemfireOnRegionFunctionTemplate customerRegionFunctionTemplate(
            @Qualifier("customer") Region<String, Customer> region) {
        return new GemfireOnRegionFunctionTemplate(region);
    }

    @Bean
    GemfireOnRegionFunctionTemplate customerOrderRegionFunctionTemplate(
            @Qualifier("customer-order") Region<String, CustomerOrder> region) {
        return new GemfireOnRegionFunctionTemplate(region);
    }

    @Bean
    GemfireOnRegionFunctionTemplate itemRegionFunctionTemplate(
            @Qualifier("item") Region<String, Item> region) {
        return new GemfireOnRegionFunctionTemplate(region);
    }
}