package io.pivotal.demo.pcc.client.controller;

import io.pivotal.demo.pcc.client.exception.ResourceNotFoundException;
import io.pivotal.demo.pcc.model.gf.Item;
import io.pivotal.demo.pcc.model.io.ItemIO;
import io.pivotal.demo.pcc.model.mapper.ItemMapper;
import io.pivotal.demo.pcc.repository.gf.ItemRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/items")
public class ItemController {
    private final ItemRepository itemRepository;

    @GetMapping("/{id}")
    public ItemIO getItemById(@PathVariable String id) {
        Item item = itemRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException());
        return ItemMapper.MAPPER.getItemIO(item);
    }

    @PutMapping("/{id}")
    public void updateItemById(@PathVariable String id, @RequestBody ItemIO itemIO) {
        Item item = ItemMapper.MAPPER.getItem(itemIO);
        itemRepository.save(item);
    }
}
