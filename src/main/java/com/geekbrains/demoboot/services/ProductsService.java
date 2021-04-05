package com.geekbrains.demoboot.services;

import com.geekbrains.demoboot.entities.Product;
import com.geekbrains.demoboot.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class ProductsService {
    private ProductRepository productRepository;

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getById(Long id) {
        return productRepository.findById(id).get();
    }

    public List<Product> getAllProducts(Specification<Product> specification) {
        List<Product> products = productRepository.findAll(specification);
        return products;
    }
    public long getAmountOfProducts() {
        return productRepository.count();
    }

    public void add(Product product) {
        productRepository.save(product);
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    public void edit(Product product) {
        Specification<Product> specification = Specification.where(null);
        Iterator<Product> iterator = productRepository.findAll(specification).iterator();
        while (iterator.hasNext()) {
            if(product.getId().equals(iterator.next().getId())) {
                iterator.next().setTitle(product.getTitle());
                iterator.next().setPrice(product.getPrice());
            }
        }
        productRepository.save(product);
    }

    public Product incrementView(Product product) {
        product.setViews(product.getViews() + 1);
        return productRepository.save(product);
    }

    public List<Product> getMostPopular() {
        List<Product> products = productRepository.getMostPopular();
        return products.subList(0, 3);
    }
}
