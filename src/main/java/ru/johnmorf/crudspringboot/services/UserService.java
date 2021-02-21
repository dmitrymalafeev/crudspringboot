package ru.johnmorf.crudspringboot.services;

import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.johnmorf.crudspringboot.entities.Role;
import ru.johnmorf.crudspringboot.entities.User;
import ru.johnmorf.crudspringboot.repositories.RoleRepository;
import ru.johnmorf.crudspringboot.repositories.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UserService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getUser(Long id) {
        return userRepository.findById(id).get();
    }

    public User save(User user) {
        refreshRoles(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User update(Long idUserFromDb, User updateUser) {
        User userFromDb = userRepository.findById(idUserFromDb).get();
        Collection<Role> oldRoles = userFromDb.getRoles();
        refreshRoles(updateUser);
        updateUser.setPassword(passwordEncoder.encode(updateUser.getPassword()));
        BeanUtils.copyProperties(updateUser, userFromDb, "id");
        oldRoles.forEach(roleRepository::delete);

        return userRepository.save(userFromDb);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    private void refreshRoles(User user) {
        List<Role> newRoles = new ArrayList<>();
        String[] rolesStrings = user.getRolesStrings();
        for (int i = 0; i < rolesStrings.length; i++) {
            newRoles.add(new Role(rolesStrings[i]));
        }
        user.setRoles(newRoles);
    }
}
