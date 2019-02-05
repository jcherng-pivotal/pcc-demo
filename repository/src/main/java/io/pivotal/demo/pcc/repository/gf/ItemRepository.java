package io.pivotal.demo.pcc.repository.gf;

import io.pivotal.demo.pcc.model.gf.Item;
import org.springframework.data.gemfire.repository.GemfireRepository;

public interface ItemRepository extends GemfireRepository<Item, String> {

}
