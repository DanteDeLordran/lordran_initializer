package dev.darsaras.initializer.auth.controllers;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.darsaras.initializer.auth.models.User;
import dev.darsaras.initializer.auth.services.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> registerCustomer( @RequestBody User user ){
        return authService.registerUser(user);
    }

    @GetMapping("/login")
    public ResponseEntity<Optional<User>> getUserDetailsAfterLogin(Authentication authentication){
        return authService.getUserDetailsAfterLogin(authentication);
    }

}