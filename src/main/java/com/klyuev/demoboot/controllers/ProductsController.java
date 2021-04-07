package com.klyuev.demoboot.controllers;

import com.klyuev.demoboot.entities.Product;
import com.klyuev.demoboot.entities.User;
import com.klyuev.demoboot.repositories.UserRepository;
import com.klyuev.demoboot.repositories.specifications.ProductSpecification;
import com.klyuev.demoboot.services.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductsController {
    private ProductsService productsService;
    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) { this.userRepository = userRepository; }

    @Autowired
    public void setProductsService(ProductsService productsService) {
        this.productsService = productsService;
    }

    @GetMapping
    public String showProductsList(Principal principal, Model model, @RequestParam(value = "word", required = false) String word,
                                   @RequestParam(value = "minPrice", required = false) Integer minPrice,
                                   @RequestParam(value = "maxPrice", required = false) Integer maxPrice,
                                   @RequestParam(value = "page", required = false) Integer page) {
        if(principal != null) {
            User user = userRepository.findOneByUsername(principal.getName());
            model.addAttribute("username", user.getName());
        }
        Specification<Product> productSpecification = Specification.where(null);
        if(word != null) {
          productSpecification = productSpecification.and(ProductSpecification.titleContains(word));
       }
        if(minPrice != null) {
            productSpecification = productSpecification.and(ProductSpecification.greaterOrEqualsThan(minPrice));
        }
        if(maxPrice != null) {
            productSpecification = productSpecification.and(ProductSpecification.lessOrEqualsThan(maxPrice));
        }
        if(page == null) {
            page = 1;
        }
        List<Product> mostPopularProducts = productsService.getMostPopular();
        List<Product> products = productsService.getAllProducts(productSpecification);
        System.out.println(products.size());
        System.out.println(mostPopularProducts.size());
        long amount = productsService.getAmountOfProducts();
        model.addAttribute("amount", amount);
        model.addAttribute("product", new Product());
        model.addAttribute("products", products);
        model.addAttribute("mostPopularProducts", mostPopularProducts);
        model.addAttribute("word", word);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);
        return "products";
    }
    @GetMapping("/add")
    @Secured(value = "ROLE_ADMIN")
    public String addProduct(Model model) {
        model.addAttribute("product", new Product());
        return "add-product";
    }
    @PostMapping("/add")
    @Secured(value = "ROLE_ADMIN")
    public String addProduct(@ModelAttribute(value = "product")Product product) {
        productsService.add(product);
        return "redirect:/products";
    }
    @GetMapping("/show/{id}")
    public String showOneProduct(Model model, @PathVariable(value = "id") Long id) {
        Product product = productsService.getById(id);
        product = productsService.incrementView(product);
        model.addAttribute("product", product);
        return "product-page";
    }
    @DeleteMapping("/show/{id}/delete")
    @Secured(value = "ROLE_ADMIN")
    public String deleteProduct(@PathVariable(value = "id") Long id) {
        productsService.delete(id);
        return "redirect:/products";
    }
    @GetMapping("/edit/{id}")
    @Secured(value = "ROLE_ADMIN")
    public String editProduct(Model model, @PathVariable(value = "id") Long id) {
        Product product = productsService.getById(id);
        model.addAttribute("product", product);
        return "edit-product";
    }
    @PostMapping("/edit/{id}")
    @Secured(value = "ROLE_ADMIN")
    public String editProduct(@ModelAttribute(value = "product") Product product) {
        productsService.edit(product);
        return "redirect:/products";
    }

}
