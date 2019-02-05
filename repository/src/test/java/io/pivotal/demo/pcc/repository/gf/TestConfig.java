package io.pivotal.demo.pcc.repository.gf;

import org.apache.geode.cache.RegionShortcut;
import org.springframework.data.gemfire.config.annotation.EnableEntityDefinedRegions;
import org.springframework.data.gemfire.config.annotation.EnablePdx;
import org.springframework.data.gemfire.config.annotation.PeerCacheApplication;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;

@PeerCacheApplication
@EnablePdx
@EnableEntityDefinedRegions(basePackages = {"io.pivotal.demo.pcc.model.gf"},
        serverRegionShortcut = RegionShortcut.PARTITION)
@EnableGemfireRepositories(basePackages = {"io.pivotal.demo.pcc.repository.gf"})
public class TestConfig {
}
