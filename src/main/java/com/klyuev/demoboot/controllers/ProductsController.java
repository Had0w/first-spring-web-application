package com.klyuev.demoboot.controllers;

import com.klyuev.demoboot.entities.Product;
import com.klyuev.demoboot.entities.User;
import com.klyuev.demoboot.repositories.UserRepository;
import com.klyuev.demoboot.repositories.specifications.ProductSpecification;
import com.klyuev.demoboot.services.ProductsService;
import com.klyuev.demoboot.services.UserService;
import com.klyuev.demoboot.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private UserServiceImpl userService;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setProductsService(ProductsService productsService) {
        this.productsService = productsService;
    }

    @Autowired
    public void setUserService(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showProductsList(Principal principal, Model model, @RequestParam(value = "word", required = false) String word,
                                   @RequestParam(value = "minPrice", required = false) Integer minPrice,
                                   @RequestParam(value = "maxPrice", required = false) Integer maxPrice,
                                   @RequestParam(value = "page", required = false) Integer page) {
        if (principal != null) {
            User user = userRepository.findOneByUsername(principal.getName());
            model.addAttribute("username", user.getName());
        }
        Specification<Product> productSpecification = Specification.where(null);
        if (word != null) {
            productSpecification = productSpecification.and(ProductSpecification.titleContains(word));
        }
        if (minPrice != null) {
            productSpecification = productSpecification.and(ProductSpecification.greaterOrEqualsThan(minPrice));
        }
        if (maxPrice != null) {
            productSpecification = productSpecification.and(ProductSpecification.lessOrEqualsThan(maxPrice));
        }
        if (page == null) {
            page = 1;
        }
        List<Product> mostPopularProducts = productsService.getMostPopular();
        List<Product> products = productsService.getAllProducts(productSpecification, PageRequest.of(page, 5));
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
    public String addProduct(@ModelAttribute(value = "product") Product product) {
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

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login() {
        return "redirect:/products";
    }

    @PostMapping("/identificateTheUser")
    public String identificateTheUser(Principal principal, @ModelAttribute(value = "login") String login,
                                      @ModelAttribute(value = "password") String password) {

        return "redirect:/products";
    }

    @GetMapping("/registration")
    public String registrationForm(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String registrationForm(@ModelAttribute(value = "user") User user) {
        String oldPassword = user.getPassword();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        String newPassword = passwordEncoder.encode(oldPassword);
        user.setPassword(newPassword);
        userService.addUser(user);
        return "redirect:/products";
    }
}
