package io.pivotal.demo.pcc.repository.jpa;

import io.pivotal.demo.pcc.model.jpa.CustomerEntity;
import io.pivotal.demo.pcc.model.jpa.CustomerOrderEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface CustomerOrderEntityRepository extends JpaRepository<CustomerOrderEntity, String> {
    Sort DEFAULT_SORT = Sort.by("id");

    Set<CustomerOrderEntity> findByCustomer(CustomerEntity customer);
}
