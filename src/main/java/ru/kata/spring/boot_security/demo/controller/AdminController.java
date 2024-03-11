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

//    @GetMapping(value = "admin/all")
//    public String allUsers(@ModelAttribute("user") User user, Principal principal, ModelMap modelMap, @RequestParam(required = false) String roleAdmin, @RequestParam(required = false) String roleUser) {
//        modelMap.addAttribute("user", userService.loadUserByUsername(principal.getName()));
//        modelMap.addAttribute("users", userService.getAllUsers());
////        modelMap.addAttribute("roles", roleService.allRoles());
//        Set<Role> roles = new HashSet<>();
//        if (roleAdmin != null && roleAdmin.equals("1")) {
//            roles.add(roleService.getRoleById(1));
//        }
//        if (roleUser != null && roleUser.equals("2")) {
//            roles.add(roleService.getRoleById(2));
//        }
//        user.setRoleSet(roles);
//        return "x";
//    }

    @PostMapping("/admin/editUser")
    public String editUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/admin/all";
    }

//    @GetMapping(value = "admin/add")
//    public String addUser(Model model) {
//        User user = new User();
//        model.addAttribute("user", user);
//        return "addUser";
//    }
//
//    @PostMapping(value = "admin/add")
//    public String saveUser(@ModelAttribute("user") User user, @RequestParam(required = false) String roleAdmin, @RequestParam(required = false) String roleUser) {
//        Set<Role> roles = new HashSet<>();
//        if (roleAdmin != null && roleAdmin.equals("1")) {
//            roles.add(roleService.getRoleById(1));
//        }
//        if (roleUser != null && roleUser.equals("2")) {
//            roles.add(roleService.getRoleById(2));
//        }
//        user.setRoleSet(roles);
//        userService.saveUser(user);
//        return "redirect:/admin/all";
//    }
//
//    @GetMapping(value = "admin/edit/{id}")
//    public String editUser(ModelMap modelMap, @PathVariable("id") int id) {
//        User user = userService.getUser(id);
//        Set<Role> roles = new HashSet<>();
//        for (Role role: roles) {
//            if (role.equals(roleService.getRoleById(1))) {
//                modelMap.addAttribute("roleAdmin", true);
//            }
//            if (role.equals(roleService.getRoleById(2))) {
//                modelMap.addAttribute("roleUser", true);
//            }
//        }
//        modelMap.addAttribute("user", user);
//        System.out.println(user);
//        return "editUser";
//    }
//
//    @PostMapping(value = "admin/edit")
//    public String postEditUser(@ModelAttribute("user") User user, @RequestParam(required = false) String roleAdmin, @RequestParam(required = false) String roleUser) {
//        Set<Role> roles = new HashSet<>();
//        if (roleAdmin != null && roleAdmin.equals("1")) {
//            roles.add(roleService.getRoleById(1));
//        }
//        if (roleUser != null && roleUser.equals("2")) {
//            roles.add(roleService.getRoleById(2));
//        }
//        user.setRoleSet(roles);
//        userService.saveUser(user);
//        return "redirect:/admin/all";
//    }
//
    @GetMapping("/admin/deleteUser")
    public String deleteUser(@RequestParam("userId") int id) {
        userService.deleteUser(id);
        return "redirect:/admin/all";
    }

    @PostMapping("/admin/saveUser")
    public String saveUser(@ModelAttribute("newUser") User user) {
        userService.saveUser(user);

        return "redirect:/admin/all";
    }
}
