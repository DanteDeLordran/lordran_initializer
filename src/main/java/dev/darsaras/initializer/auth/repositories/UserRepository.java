package dev.darsaras.initializer.auth.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import dev.darsaras.initializer.auth.models.User;

public interface UserRepository extends CrudRepository<User,Long>{
    public Optional<User> findByEmail( String email );
}
