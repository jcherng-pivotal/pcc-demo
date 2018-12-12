package io.pivotal.demo.pcc.repository.jpa;

import io.pivotal.demo.pcc.model.jpa.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface CustomerEntityRepository extends JpaRepository<CustomerEntity, String> {
    Set<CustomerEntity> findByName(String name);
}
