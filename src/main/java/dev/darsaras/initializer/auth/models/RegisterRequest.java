package dev.darsaras.initializer.auth.models;

public record RegisterRequest(
    String username,
    String email,
    String password
    
) {}
