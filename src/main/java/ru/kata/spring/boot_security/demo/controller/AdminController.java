package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;
import java.util.Map;


@Controller
public class AdminController {

    private final UserService userService;

    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping("/admin")
    public String showAllUsers(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        String userRoles = userService.getUserRoles(user);
        model.addAttribute("user", user);
        model.addAttribute("userRoles", userRoles);

        Map<User, String> usersWithRoles = userService.getAllUsersWithRoles();
        model.addAttribute("usersWithRoles", usersWithRoles);

        return "admin";
    }

    @PostMapping(value = "/admin/addUser")
    public String addNewUser(@RequestParam("role") List<String> roles, @ModelAttribute("user") User user) {
        userService.addNewUser(roles, user);
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
    public String updateUser(@RequestParam("role") List<String> roles, @ModelAttribute("user") User user) {
        userService.updateUser(roles, user);
        return "redirect:/admin";
    }

    @PostMapping(value = "/admin/deleteUser")
    public String deleteUser(@RequestParam long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}