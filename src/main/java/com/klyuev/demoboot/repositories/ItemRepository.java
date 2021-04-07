package com.klyuev.demoboot.repositories;

import com.klyuev.demoboot.entities.Item;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ItemRepository extends PagingAndSortingRepository<Item, Long>, JpaSpecificationExecutor<Item> {
   Item findByTitle(String title);
}
