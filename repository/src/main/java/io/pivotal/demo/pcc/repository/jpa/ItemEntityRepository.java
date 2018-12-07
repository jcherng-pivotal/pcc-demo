package io.pivotal.demo.pcc.repository.jpa;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import io.pivotal.demo.pcc.model.jpa.ItemEntity;

public interface ItemEntityRepository extends CrudRepository<ItemEntity, String> {
	Set<ItemEntity> findByName(String name);
}
