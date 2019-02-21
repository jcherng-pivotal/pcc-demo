package io.pivotal.demo.pcc.client.config;

import org.apache.geode.cache.client.ClientRegionShortcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.config.annotation.EnableEntityDefinedRegions;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;

@Configuration
@EnableEntityDefinedRegions(basePackages = {"io.pivotal.demo.pcc.model.gf"},
        clientRegionShortcut = ClientRegionShortcut.PROXY)
@EnableGemfireRepositories(basePackages = {"io.pivotal.demo.pcc.repository.gf"})
public class GemFireConfig {

}
