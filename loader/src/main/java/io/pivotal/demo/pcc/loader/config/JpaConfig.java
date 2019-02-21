package io.pivotal.demo.pcc.loader.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = {"io.pivotal.demo.pcc.model.jpa"})
@EnableJpaRepositories(basePackages = {"io.pivotal.demo.pcc.repository.jpa"})
public class JpaConfig {
}
