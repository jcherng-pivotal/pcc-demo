package io.pivotal.demo.pcc.repository.jpa;

import io.pivotal.demo.pcc.model.jpa.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface ItemEntityRepository extends JpaRepository<ItemEntity, String> {
    Set<ItemEntity> findByName(String name);
}
