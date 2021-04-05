package com.geekbrains.demoboot.controllers;

import com.geekbrains.demoboot.entities.Item;
import com.geekbrains.demoboot.entities.Product;
import com.geekbrains.demoboot.repositories.specifications.ItemSpecification;
import com.geekbrains.demoboot.services.ItemService;
import com.sun.scenario.effect.impl.sw.java.JSWEffectPeer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.awt.print.Pageable;
import java.util.List;

@Controller
@RequestMapping("/items")
public class ItemController {
    private ItemService itemService;

    @Autowired
    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
    }
    @GetMapping
    public String getProducts(Model model) {
        Specification<Item> specification = Specification.where(null);
        specification = specification.and(ItemSpecification.greaterOrEquals(11));
        List<Item> items = itemService.getAllItem(specification, PageRequest.of(1, 2)).getContent();
        model.addAttribute("items", items);
        return "items";
    }
}
