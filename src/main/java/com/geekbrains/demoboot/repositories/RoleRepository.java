package com.geekbrains.demoboot.repositories;

import com.geekbrains.demoboot.entities.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findOneByName(String name);
}
