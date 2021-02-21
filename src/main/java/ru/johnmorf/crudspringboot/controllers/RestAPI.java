package ru.johnmorf.crudspringboot.controllers;

import org.springframework.web.bind.annotation.*;
import ru.johnmorf.crudspringboot.entities.User;
import ru.johnmorf.crudspringboot.services.UserService;

import java.util.List;

@RestController
@RequestMapping("admin/api/users")
public class RestAPI {

    private final UserService userService;

    public RestAPI(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> list() {
        return userService.getAll();
    }

    @GetMapping("{id}")
    public User getOne(@PathVariable("id") Long id) {
        return userService.getUser(id);
    }

    @PostMapping
    public User create(@RequestBody User user) {
        return userService.save(user);
    }

    @PutMapping("{id}")
    public User update(@PathVariable("id") Long idUserFromDb, @RequestBody User user) {
        return userService.update(idUserFromDb, user);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") User user) {
        userService.delete(user);
    }
}
