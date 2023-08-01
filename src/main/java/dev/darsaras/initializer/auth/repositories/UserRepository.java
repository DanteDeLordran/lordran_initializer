package dev.darsaras.initializer.auth.repositories;

import org.springframework.data.repository.CrudRepository;

import dev.darsaras.initializer.auth.models.User;

public interface UserRepository extends CrudRepository<User,Long>{
    public String findByEmail( String email );
}
