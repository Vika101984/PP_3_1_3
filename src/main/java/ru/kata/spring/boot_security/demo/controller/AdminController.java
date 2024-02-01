package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@Controller
public class AdminController {

    private final UserService userService;

    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping("/admin")
    public String showAllUsers(Model model) {
        List<User> users = userService.showAllUsers();
        model.addAttribute("users", users);
        return "admin";
    }

    @PostMapping(value = "/admin/addUser")
    public String addNewUser(@RequestParam("role") String role, @ModelAttribute("user") User user) {
        userService.addNewUser(role,user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/updateUser")
    public String editUserForm(@RequestParam("id") Long id, Model model) {
        User userUpdate = userService.findById(id).orElse(null);
        List<Role> roles = roleService.findAll();
        model.addAttribute("user", userUpdate);
        model.addAttribute("listRoles", roles);
        return "admin";
    }

    @PostMapping(value = "/admin/updateUser")
    public String updateUser(@RequestParam("role") String role, @ModelAttribute("user") User user) {
        userService.updateUser(role ,user);
        return "redirect:/admin";
    }

    @PostMapping(value = "/admin/deleteUser")
    public String deleteUser(@RequestParam long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
