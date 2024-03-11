package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

//    @GetMapping(value = "/lk")
//    public String getUserPage(ModelMap modelMap, Principal principal) {
//        modelMap.addAttribute("user", userService.loadUserByUsername(principal.getName()));
//        return "userPage";
//    }
//
//    @GetMapping(value = "/{id}")
//    public String show(@PathVariable("id") int id, ModelMap modelMap) {
//        modelMap.addAttribute("user", userService.getUser(id));
//        return "userPage";
//    }
    @GetMapping("")
    public String userInfo(Principal principal, Model model) {
        User user = (User) userService.loadUserByUsername(principal.getName());
        if (user == null) {
            return "redirect:/";
        }
        model.addAttribute("currentUser", user);
        return "user";
    }
}
