package ru.johnmorf.crudspringboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.johnmorf.crudspringboot.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String username);
}
