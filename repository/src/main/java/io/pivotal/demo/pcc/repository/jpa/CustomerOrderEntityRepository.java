package io.pivotal.demo.pcc.repository.jpa;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import io.pivotal.demo.pcc.model.jpa.CustomerEntity;
import io.pivotal.demo.pcc.model.jpa.CustomerOrderEntity;

public interface CustomerOrderEntityRepository extends CrudRepository<CustomerOrderEntity, String> {
	Set<CustomerOrderEntity> findByCustomer(CustomerEntity customer);
}
