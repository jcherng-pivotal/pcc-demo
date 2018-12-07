package io.pivotal.demo.pcc.repository.jpa;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {"io.pivotal.demo.pcc.model.jpa"})
@EnableJpaRepositories(basePackages = {"io.pivotal.demo.pcc.repository.jpa"})
public class TestConfig {
}
