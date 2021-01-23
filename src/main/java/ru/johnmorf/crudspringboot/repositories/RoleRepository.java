package ru.johnmorf.crudspringboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.johnmorf.crudspringboot.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
