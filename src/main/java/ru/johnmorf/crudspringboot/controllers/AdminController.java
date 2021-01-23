package ru.johnmorf.crudspringboot.controllers;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.johnmorf.crudspringboot.entities.User;
import ru.johnmorf.crudspringboot.repositories.UserRepository;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public AdminController(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @GetMapping()
    public String showAllUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "users";
    }

    @GetMapping("/new")
    public String addUser(@ModelAttribute("user") User user) {
        return "newUser";
    }

    @PostMapping("/new")
    public String createUser(@ModelAttribute("user")User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.refreshRoles();
        userRepository.save(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/update")
    public String editUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userRepository.findById(id).get());
        return "updateUser";
    }

    @PostMapping("/{id}/update")
    public String updateUser(@ModelAttribute("user") User user, @PathVariable Long id) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.refreshRoles();
        userRepository.save(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/delete")
    public String deleteUser(@PathVariable Long id) {
        userRepository.delete(userRepository.findById(id).get());
        return "redirect:/admin";
    }
}
