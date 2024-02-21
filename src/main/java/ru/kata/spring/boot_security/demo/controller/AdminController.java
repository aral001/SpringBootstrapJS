package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.HashSet;
import java.util.Set;

@Controller
public class AdminController {
    private final UserService userService;

    private final RoleService roleService;
    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value = "admin/all")
    public String allUsers(ModelMap modelMap) {
        modelMap.addAttribute("users", userService.getAllUsers());
        return "allUsersPage";
    }

    @GetMapping(value = "admin/add")
    public String addUser(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "addUser";
    }
    
    @PostMapping(value = "admin/add")
    public String saveUser(@ModelAttribute("user") User user, @RequestParam(required = false) String roleAdmin) {
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getRoleById(2));
        if (roleAdmin != null && roleAdmin.equals("1")) {
            roles.add(roleService.getRoleById(1));
        }
        user.setRoleSet(roles);
        userService.saveUser(user);
        return "redirect:/admin/all";
    }

    @GetMapping(value = "admin/edit/{id}")
    public String editUser(ModelMap modelMap, @PathVariable("id") int id) {
        User user = userService.getUser(id);
        Set<Role> roles = new HashSet<>();
        for (Role role: roles) {
            if (role.equals(roleService.getRoleById(1))) {
                modelMap.addAttribute("roleAdmin", true);
            }
        }
        modelMap.addAttribute("user", user);
        System.out.println(user);
        return "editUser";
    }

    @PostMapping(value = "admin/edit")
    public String postEditUser(@ModelAttribute("user") User user, @RequestParam(required = false) String roleAdmin) {
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getRoleById(2));
        if (roleAdmin != null && roleAdmin.equals("1")) {
            roles.add(roleService.getRoleById(1));
        }
        user.setRoleSet(roles);
        userService.saveUser(user);
        return "redirect:/admin/all";
    }

    @GetMapping("/admin/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return "redirect:/admin/all";
    }
}
