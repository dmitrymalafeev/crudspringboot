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

    private final UserRepository userRepository;

    public AdminController(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping()
    public String showAdminPage(Principal principal, Model model) {
        model.addAttribute("user", userRepository.findByEmail(principal.getName()));
        return "users";
    }

}
