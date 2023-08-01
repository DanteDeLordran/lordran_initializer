package dev.darsaras.initializer.auth.services;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dev.darsaras.initializer.auth.models.User;
import dev.darsaras.initializer.auth.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<String> registerUser( User user) {
        try {
            String hashedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(hashedPassword);
            userRepository.save(user);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("Given user details are successfully registered");

        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An exception occured due to " + ex.getMessage());
        }
    
    }

    //This method helps us to store the Customer details on the session storage on the frontend
    public ResponseEntity<Optional<User>> getUserDetailsAfterLogin(Authentication authentication){
        Optional<User> customer = userRepository.findByEmail(authentication.getName());
        if (customer.isPresent()) {
            return ResponseEntity.ok(customer);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
