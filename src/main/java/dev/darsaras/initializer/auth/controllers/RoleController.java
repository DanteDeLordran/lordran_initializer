package dev.darsaras.initializer.auth.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.darsaras.initializer.auth.services.RoleService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/roles")
public class RoleController {
    
    private final RoleService roleService;

    @PostMapping("/seed")
    public ResponseEntity<String> roleSeed(){
        return roleService.roleSeed();
    }

}
