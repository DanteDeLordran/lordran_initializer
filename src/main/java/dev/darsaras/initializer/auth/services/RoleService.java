package dev.darsaras.initializer.auth.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import dev.darsaras.initializer.auth.models.Role;
import dev.darsaras.initializer.auth.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleService {
    
    private final RoleRepository roleRepository;

    public ResponseEntity<String> roleSeed(){
        if (roleRepository.findAll().size() == 0) {
            Role roleAdmin = new Role();
            roleAdmin.setName("ROLE_ADMIN");
            Role roleMod = new Role();
            roleMod.setName("ROLE_MOD");
            Role roleUser = new Role();
            roleUser.setName("ROLE_USER");
            roleRepository.save(roleAdmin);
            roleRepository.save(roleMod);
            roleRepository.save(roleUser);
            return ResponseEntity.status(HttpStatus.CREATED).body("Role seed succesfully excecuted");
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Seed already excecuted");
        }
        
    }

}
