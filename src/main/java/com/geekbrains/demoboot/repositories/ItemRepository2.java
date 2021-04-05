package com.geekbrains.demoboot.repositories;

import com.geekbrains.demoboot.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository2 extends JpaRepository<Item, Long> {
}
