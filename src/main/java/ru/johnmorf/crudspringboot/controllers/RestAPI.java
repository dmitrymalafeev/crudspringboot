package ru.johnmorf.crudspringboot.controllers;

import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.johnmorf.crudspringboot.entities.Role;
import ru.johnmorf.crudspringboot.entities.User;
import ru.johnmorf.crudspringboot.repositories.RoleRepository;
import ru.johnmorf.crudspringboot.repositories.UserRepository;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("admin/api/users")
public class RestAPI {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RestAPI(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public List<User> list() {
        return userRepository.findAll();
    }

    @GetMapping("{id}")
    public User getOne(@PathVariable("id") User user) {
        return user;
    }

    @PostMapping
    public User create(@RequestBody User user) {
        user.refreshRoles();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @PutMapping("{id}")
    public User update(@PathVariable("id") User userFromDb, @RequestBody User user) {
        Collection<Role> oldRoles = userFromDb.getRoles();
        user.refreshRoles();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        BeanUtils.copyProperties(user, userFromDb, "id");
        oldRoles.forEach(roleRepository::delete);

        return userRepository.save(userFromDb);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") User user) {
        userRepository.delete(user);
    }
}
