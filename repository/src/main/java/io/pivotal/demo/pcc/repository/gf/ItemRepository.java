package io.pivotal.demo.pcc.repository.gf;

import io.pivotal.demo.pcc.model.gf.pdx.Item;
import org.springframework.data.gemfire.repository.GemfireRepository;

public interface ItemRepository extends GemfireRepository<Item, String> {

}
