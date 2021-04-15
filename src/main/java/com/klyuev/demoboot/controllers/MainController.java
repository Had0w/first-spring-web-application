package com.klyuev.demoboot.controllers;

import com.klyuev.demoboot.entities.User;
import com.klyuev.demoboot.services.ProductsService;
import com.klyuev.demoboot.services.UserService;
import com.klyuev.demoboot.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
//@RequestMapping("/main")
public class MainController {
    private UserServiceImpl userService;

    @Autowired
    public void setUserService(UserServiceImpl userService) {
        this.userService = userService;
    }

    //    @GetMapping
//    public String index() {
//        return "index";
//    }
//
//    @GetMapping("/secured")
//    public String secured() {
//        return "index";
//    }
//
    @GetMapping("/login")
    public String loginForm() {
        return "login";
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
        userService.addUser(user);
        return "redirect:/products";
    }
}
