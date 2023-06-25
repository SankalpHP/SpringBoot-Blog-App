package net.javaguides.springboot.repository;

import net.javaguides.springboot.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRespository extends JpaRepository<Role,Long> {

    // Create a query method
    Role findByName(String name);
}
