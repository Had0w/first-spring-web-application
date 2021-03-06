package com.klyuev.demoboot.services;

import com.klyuev.demoboot.entities.Item;
import com.klyuev.demoboot.repositories.ItemRepository;
import com.klyuev.demoboot.repositories.ItemRepository2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
    private ItemRepository itemRepository;
    private ItemRepository2 itemRepository2;

    @Autowired
    public void setItemRepository2(ItemRepository2 itemRepository2) {this.itemRepository2 = itemRepository2; }

    @Autowired
    public void setItemRepository(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Page<Item> getAllItem(Specification<Item> specification, Pageable pageable) {
        return itemRepository.findAll(specification, pageable);
    }

    public Item findByTitle(String title) {
        return itemRepository.findByTitle(title);
    }
}
