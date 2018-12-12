package io.pivotal.demo.pcc.client.config;

import io.pivotal.demo.pcc.repository.gf.CustomerOrderRepository;
import org.apache.geode.cache.GemFireCache;
import org.apache.geode.cache.client.ClientRegionShortcut;
import org.apache.geode.pdx.ReflectionBasedAutoSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import org.springframework.data.gemfire.config.annotation.EnableEntityDefinedRegions;
import org.springframework.data.gemfire.config.annotation.EnablePdx;
import org.springframework.data.gemfire.config.annotation.EnableSecurity;
import org.springframework.data.gemfire.function.execution.GemfireOnRegionFunctionTemplate;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;

@ClientCacheApplication
@EnableSecurity
@EnablePdx(serializerBeanName = "reflectionBasedAutoSerializer")
@EnableEntityDefinedRegions(basePackages = {"io.pivotal.demo.pcc.model.gf.pdx"},
        clientRegionShortcut = ClientRegionShortcut.PROXY)
@EnableGemfireRepositories(basePackages = {"io.pivotal.demo.pcc.repository.gf"})
public class GemFireConfig {
    @Bean
    ReflectionBasedAutoSerializer reflectionBasedAutoSerializer() {
        return new ReflectionBasedAutoSerializer("io.pivotal.demo.pcc.model.*");
    }

    @Bean
    GemfireOnRegionFunctionTemplate customerOrderRegionFunctionTemplate(GemFireCache cache, CustomerOrderRepository customerOrderRepository) {
        return new GemfireOnRegionFunctionTemplate(cache.getRegion("customer-order"));
    }
}
