package com.geekbrains.demoboot.repositories;

import com.geekbrains.demoboot.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import sun.security.mscapi.CPublicKey;

import java.util.List;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    @Query("SELECT p FROM Product p ORDER BY p.views DESC")
    public List<Product> getMostPopular();
}

