package io.pivotal.demo.pcc.repository.gf;

import io.pivotal.demo.pcc.model.gf.pdx.Item;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<Item, String> {

}
