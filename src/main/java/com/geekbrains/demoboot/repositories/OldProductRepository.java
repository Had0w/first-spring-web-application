package com.geekbrains.demoboot.repositories;

import com.geekbrains.demoboot.entities.Product;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class OldProductRepository {
    private List<Product> products;

    @PostConstruct
//    public void init() {
//        products = new ArrayList<>();
//        products.add(new Product(1L, "Bread", 40));
//        products.add(new Product(2L, "Milk", 90));
//        products.add(new Product(3L, "Cheese", 200));
//    }

    public List<Product> findAll() {
        return products;
    }

    public Product findByTitle(String title) {
        return products.stream().filter(p -> p.getTitle().equals(title)).findFirst().get();
    }

    public Product findById(Long id) {
        return products.stream().filter(p -> p.getId().equals(id)).findFirst().get();
    }

    public void save(Product product) {
        products.add(product);
    }

    public void delete(Long id) {
        Product deleteProduct = products.stream().filter(p -> p.getId().equals(id)).findFirst().get();
        products.remove(deleteProduct);
    }
    public void edit(Product product) {
        Iterator<Product> iterator = products.iterator();
        while(iterator.hasNext()) {
            Product product1 = iterator.next();
            if(product1.getId().equals(product.getId())) {
                product1.setTitle(product.getTitle());
                product1.setPrice(product.getPrice());
            }
        }
    }
}
