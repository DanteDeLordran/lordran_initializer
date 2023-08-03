package dev.darsaras.initializer.auth.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.darsaras.initializer.auth.models.Role;

public interface RoleRepository extends JpaRepository<Role,Integer> {
    public Role findByName(String roleName);
}
