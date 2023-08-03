package dev.darsaras.initializer.auth.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dev.darsaras.initializer.auth.models.RegisterRequest;
import dev.darsaras.initializer.auth.models.Role;
import dev.darsaras.initializer.auth.models.User;
import dev.darsaras.initializer.auth.repositories.RoleRepository;
import dev.darsaras.initializer.auth.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<String> register(RegisterRequest request) {
        try {
            Role userRole = roleRepository.findByName("ROLE_USER");
            Set<Role> roles = new HashSet<>();
            roles.add(userRole);
            User user = User.builder()
                    .username(request.username())
                    .email(request.email())
                    .password(passwordEncoder.encode(request.password()))
                    .build();
            user.setRoles(roles);
            // String hashedPassword = passwordEncoder.encode(user.getPassword());
            // user.setPassword(hashedPassword);
            userRepository.save(user);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("User successfully registered");

        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An exception occured due to " + ex.getMessage());
        }

    }

    // This method helps us to store the Customer details on the session storage on
    // the frontend
    public ResponseEntity<Optional<User>> getUserDetailsAfterLogin(Authentication authentication) {
        Optional<User> customer = userRepository.findByEmail(authentication.getName());
        if (customer.isPresent()) {
            return ResponseEntity.ok(customer);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
