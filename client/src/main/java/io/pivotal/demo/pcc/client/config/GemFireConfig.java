package io.pivotal.demo.pcc.client.config;

import org.apache.geode.cache.client.ClientRegionShortcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import org.springframework.data.gemfire.config.annotation.EnableEntityDefinedRegions;
import org.springframework.data.gemfire.config.annotation.EnablePdx;
import org.springframework.data.gemfire.function.config.EnableGemfireFunctionExecutions;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;

@Configuration
@ClientCacheApplication
@EnablePdx
@EnableEntityDefinedRegions(basePackages = {"io.pivotal.demo.pcc.model.gf.pdx"},
        clientRegionShortcut = ClientRegionShortcut.PROXY)
@EnableGemfireRepositories(basePackages = {"io.pivotal.demo.pcc.repository.gf"})
@EnableGemfireFunctionExecutions(basePackageClasses = io.pivotal.demo.pcc.client.function.CustomerOrderFunction.class)
public class GemFireConfig {

}
