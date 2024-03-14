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

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
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
    @GetMapping(value = "/admin/all")
    public String allUsers(Principal principal, ModelMap modelMap) {
        modelMap.addAttribute("currentUser", userService.loadUserByUsername(principal.getName()));
        modelMap.addAttribute("allUsers", userService.getAllUsers());
        modelMap.addAttribute("allRoles", roleService.allRoles());
        modelMap.addAttribute("newUser", new User());
        return "allUsersPage";
    }

    @PostMapping("/admin/editUser")
    public String editUser(@ModelAttribute("user") User user, @RequestParam("editUserRoleId") int editUserRoleId) {
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getRoleById(editUserRoleId));
        user.setRoles(roles);
        userService.saveUser(user);
        return "redirect:/admin/all";
    }

    @GetMapping("/admin/deleteUser")
    public String deleteUser(@RequestParam("userId") int id) {
        userService.deleteUser(id);
        return "redirect:/admin/all";
    }

    @PostMapping("/admin/saveUser")
    public String saveUser(@ModelAttribute("newUser") User user, @RequestParam("newUserRoleId") int newUserRoleId) {
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getRoleById(newUserRoleId));
        user.setRoles(roles);
        userService.saveUser(user);

        return "redirect:/admin/all";
    }
}
