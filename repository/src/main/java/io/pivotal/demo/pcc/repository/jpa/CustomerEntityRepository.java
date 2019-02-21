package io.pivotal.demo.pcc.repository.jpa;

import io.pivotal.demo.pcc.model.jpa.CustomerEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface CustomerEntityRepository extends JpaRepository<CustomerEntity, String> {
    Sort DEFAULT_SORT = Sort.by("id");

    Set<CustomerEntity> findByName(String name);
}
