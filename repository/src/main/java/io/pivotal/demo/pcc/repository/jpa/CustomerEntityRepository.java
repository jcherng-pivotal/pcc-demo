package io.pivotal.demo.pcc.repository.jpa;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import io.pivotal.demo.pcc.model.jpa.CustomerEntity;

public interface CustomerEntityRepository extends CrudRepository<CustomerEntity, String> {
	Set<CustomerEntity> findByName(String name);
}
