package ru.johnmorf.crudspringboot.controllers;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.johnmorf.crudspringboot.entities.User;
import ru.johnmorf.crudspringboot.repositories.UserRepository;

import java.security.Principal;

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
    public String showAllUsers(Principal principal, Model model) {
        model.addAttribute("user", userRepository.findByEmail(principal.getName()));
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("userObj", new User());
        return "users";
    }

    @PostMapping("/save")
    public String createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.refreshRoles();
        userRepository.save(user);
        return "redirect:/admin";
    }

    @PostMapping("/{id}/update")
    public String updateUser(User userUp, @PathVariable Long id) {
        User user = userRepository.findById(id).get();
        userUp.setPassword(passwordEncoder.encode(userUp.getPassword()));
        userUp.refreshRoles();
        user.setName(userUp.getName());
        user.setLastName(userUp.getLastName());
        user.setPassword(userUp.getPassword());
        user.setEmail(userUp.getEmail());
        user.setRoles(userUp.getRoles());
        user.setAge(userUp.getAge());
        userRepository.save(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/delete")
    public String deleteUser(@PathVariable Long id) {
        userRepository.delete(userRepository.findById(id).get());
        return "redirect:/admin";
    }

    @GetMapping("/{id}/get")
    @ResponseBody
    public User getUser(@PathVariable Long id) {
        return userRepository.findById(id).get();
    }
}
