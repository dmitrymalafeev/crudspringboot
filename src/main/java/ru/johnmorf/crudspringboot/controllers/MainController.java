package ru.johnmorf.crudspringboot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.johnmorf.crudspringboot.entities.Role;
import ru.johnmorf.crudspringboot.entities.User;
import ru.johnmorf.crudspringboot.services.UserService;

import java.util.Collections;


@Controller
@RequestMapping()
public class MainController {

    private final UserService userService;

    public MainController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/login/google")
    public String loginGoogle() {
        return "loginGoogle";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String newUser(@ModelAttribute("user") User user) {
        user.setRolesStrings(new String[]{"ROLE_USER"});
        userService.save(user);
        return "redirect:/login";
    }
}
